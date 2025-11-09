import axios from 'axios'
import { tokenService } from './tokenService';
import type {Router} from "vue-router";
import type { JWTTokenPair } from '@/types/auth.types';

const api = axios.create({
    baseURL: '/api'
});

api.interceptors.request.use(
    (config) => {
        const token = tokenService.getAccessToken()
        if (token) {
            config.headers['Authorization'] = `Bearer ${token}`
        }
        return config
    },
    (error) => {
        return Promise.reject(error)
    }
)

let isRefreshing = false
let failedQueue: Array<{resolve: (token: string) => void, reject: (err: any) => void}> = []

const processQueue = (error: any, token: string | null = null) => {
    failedQueue.forEach(prom => {
        if (error) {
            prom.reject(error)
        } else {
            prom.resolve(token as string)
        }
    })
    failedQueue = []
}

export const setupResponseInterceptor = (router: Router) => {
    import ('@/services/authService').then(({authService}) => {

        api.interceptors.response.use(
            (response) => response,
            async (error) => {
                const originalRequest = error.config
                const errorData = error.response?.data
                const status = error.response?.status

                if (status === 401 && errorData?.errorCode === 'ACCESS_TOKEN_EXPIRED') {

                    if (isRefreshing) {
                        return new Promise((resolve, reject) => {
                            failedQueue.push({resolve, reject})
                        }).then(token => {
                            originalRequest.headers['Authorization'] = 'Bearer ' + token
                            return api(originalRequest)
                        })
                    }

                    isRefreshing = true

                    try {
                        const tokens: JWTTokenPair = await authService.refreshToken()
                        processQueue(null, tokens.accessToken.toString())
                        originalRequest.headers['Authorization'] = 'Bearer ' + tokens.accessToken
                        return api(originalRequest)
                    } catch (refreshError) {
                        processQueue(refreshError, null)
                        tokenService.clearTokens()
                        await router.push('/login')
                        return Promise.reject(refreshError)
                    } finally {
                        isRefreshing = false
                    }
                }

                return Promise.reject(error)
            }

        )
    })
}

export default api
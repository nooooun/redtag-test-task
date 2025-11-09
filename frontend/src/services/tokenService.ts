import Cookies from 'js-cookie'

const ACCESS_TOKEN_KEY = 'accessToken'
const REFRESH_TOKEN_KEY = 'refreshToken'

const ACCESS_TOKEN_COOKIE_OPTIONS = {
    path: '/',
    secure: false
}

const REFRESH_TOKEN_COOKIE_OPTIONS = {
    path: '/',
    expires: 1,
    secure: false
}

export const tokenService = {
    saveTokens(accessToken: String, refreshToken: String): void {
        Cookies.set(ACCESS_TOKEN_KEY, accessToken.toString(), ACCESS_TOKEN_COOKIE_OPTIONS)
        Cookies.set(REFRESH_TOKEN_KEY, refreshToken.toString(), REFRESH_TOKEN_COOKIE_OPTIONS)
    },

    getAccessToken(): string | undefined {
        return Cookies.get(ACCESS_TOKEN_KEY)
    },

    getRefreshToken(): string | undefined {
        return Cookies.get(REFRESH_TOKEN_KEY)
    },

    clearTokens(): void {
        Cookies.remove(ACCESS_TOKEN_KEY)
        Cookies.remove(REFRESH_TOKEN_KEY)
    }
}
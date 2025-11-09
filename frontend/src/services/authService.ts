import api from './api';
import { tokenService } from './tokenService';
import {AxiosError} from "axios";

import type {
    AuthResponseDTO,
    RegisterDTO,
    LoginDTO,
    RefreshTokenDTO,
    JWTTokenPair
} from '@/types/auth.types';

export const authService = {

    async register(dto: RegisterDTO): Promise<AuthResponseDTO> {
        try {
            const response =
                await api.post<AuthResponseDTO>('/user/signup', dto)

            const { accessToken, refreshToken } = response.data.tokens
            tokenService.saveTokens(accessToken, refreshToken)

            return response.data
        } catch (error) {
            throw error as AxiosError
        }
    },

    async login(dto: LoginDTO): Promise<AuthResponseDTO> {
        try {
            const response =
                await api.post<AuthResponseDTO>('/user/login', dto)

            const { accessToken, refreshToken } = response.data.tokens
            tokenService.saveTokens(accessToken, refreshToken)

            return response.data
        } catch (error) {
            throw error as AxiosError
        }
    },

    async refreshToken(): Promise<JWTTokenPair> {
        const refreshToken = tokenService.getRefreshToken()
        if (!refreshToken) {
            throw new Error("No refresh token")
        }
        try {
            const dto: RefreshTokenDTO = {refreshToken}
            const response =
                await api.post<JWTTokenPair>('/user/refresh-token', dto)

            const { accessToken, refreshToken: newRefreshToken } = response.data
            tokenService.saveTokens(accessToken, newRefreshToken)

            return response.data
        } catch (error) {
            tokenService.clearTokens()

            throw error as AxiosError
        }
    },

    async logout(): Promise<void> {
        const refreshToken = tokenService.getRefreshToken()
        try {
            if (refreshToken) {
                const dto: RefreshTokenDTO = {refreshToken}
                await api.post<void>('/user/logout', dto)
            }
        } catch (error) {
            console.log("Logout failed")
        }
        tokenService.clearTokens()
    }
}
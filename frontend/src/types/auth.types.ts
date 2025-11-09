export interface RegisterDTO {
    username: String,
    email: String,
    password: String,
    role: String
}

export interface UserResponseDTO {
    username: String,
    email: String,
    role: String
}

export interface JWTTokenPair {
    accessToken: String,
    refreshToken: String
}

export interface AuthResponseDTO {
    user: UserResponseDTO
    tokens: JWTTokenPair
}

export interface LoginDTO {
    email: String,
    password: String
}

export interface RefreshTokenDTO {
    refreshToken: String
}
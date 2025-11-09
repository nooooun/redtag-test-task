import api from "./api.ts";
import type {CreateAuthorDTO, AuthorResponseDTO} from "@/types/author.types.ts";
import type {Page} from "@/types/page.types.ts";
import {AxiosError} from "axios";

export const authorService = {

    async addAuthor(dto: CreateAuthorDTO): Promise<AuthorResponseDTO> {
        try {
            const response = await api.post<AuthorResponseDTO>("/authors/add", dto)
            return response.data
        } catch (error) {
            console.log("Failed to create author: " +  error)
            throw error as AxiosError
        }
    },

    async getAuthors(page: Number, size: number, fullName?: String): Promise<Page<AuthorResponseDTO>> {
        try {
            const params: any = {
                page: page,
                size: size,
                sort: 'fullName,asc'
            }

            if (fullName && fullName.trim() !== '') {
                params.fullName = fullName.trim()
            }

            const response = await api.get<Page<AuthorResponseDTO>>("/authors", {params})
            return response.data
        } catch (error) {
            console.log("Failed to retrieve page of authors: ", error)
            throw error as AxiosError
        }
    },

    async getAuthorById(id: Number): Promise<AuthorResponseDTO> {
        try {
            const response = await api.get<AuthorResponseDTO>(`/authors/${id}`)
            return response.data
        } catch (error) {
            console.log("Failed to get author by id: " + error)
            throw error as AxiosError
        }
    },

    async deleteAuthor(id: Number): Promise<void> {
        try {
            await api.delete(`/authors/${id}/delete`)
        } catch (error) {
            console.log("Failed to delete author: " + error)
            throw error as AxiosError
        }
    }
}
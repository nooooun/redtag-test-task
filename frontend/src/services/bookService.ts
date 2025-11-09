import api from "./api.ts";
import type {
    CreateBookDTO,
    BookResponseDTO,
    UpdateBookDetailsDTO,
    BookFilterParamsDTO} from "@/types/book.types.ts";
import type {Page} from "@/types/page.types.ts";
import {AxiosError} from "axios";

const paramsSerializer = (params: any) => {
    return new URLSearchParams(params).toString()
}

export const bookService = {
    async addBook(dto: CreateBookDTO): Promise<BookResponseDTO> {
        try {
            return (await api.post<BookResponseDTO>('/books/add', dto)).data
        } catch (error) {throw error as AxiosError}
    },

    async getBooks(page: number, size: number, filters: BookFilterParamsDTO): Promise<Page<BookResponseDTO>> {
        try {
            const params = {
                page,
                size,
                sort: 'title,asc',
                ...filters
            }

            const response =
                await api.get<Page<BookResponseDTO>>('/books', {params: params, paramsSerializer})
            return response.data
        } catch (error) {
            console.error("Failed to fetch books")
            throw error as AxiosError
        }
    },

    async getFavoriteBooks(page: number, size: number, filters: BookFilterParamsDTO): Promise<Page<BookResponseDTO>> {
        try {
            const params = {
                page,
                size,
                sort: 'title,asc',
                ...filters
            }

            const response =
                await api.get<Page<BookResponseDTO>>('/books/favorite', {params: params, paramsSerializer})
            return response.data
        } catch (error) {
            console.error("Failed to fetch favorite books")
            throw error as AxiosError
        }
    },

    async getBookById(id: Number): Promise<BookResponseDTO> {
        try {
            return (await api.get<BookResponseDTO>(`/books/${id}`)).data
        } catch (error) {throw error as AxiosError}
    },

    async updateBookById(id: Number, dto: UpdateBookDetailsDTO): Promise<BookResponseDTO> {
        try {
            return (await api.patch<BookResponseDTO>(`/books/${id}/update`, dto)).data
        } catch (error) {throw error as AxiosError}
    },

    async deleteBookById(id: Number): Promise<void> {
        try {
            return (await api.delete<void>(`/books/${id}/delete`)).data
        } catch (error) {throw error as AxiosError}
    },

    async addBookToFavorite(id: Number): Promise<BookResponseDTO> {
        try {
            return (await api.patch<BookResponseDTO>(`/books/${id}/add-to-favorite`)).data
        } catch (error) {throw error as AxiosError}
    },

    async deleteFromFavorite(id: Number): Promise<BookResponseDTO> {
        try {
            return (await api.delete<BookResponseDTO>(`/books/${id}/delete-from-favorite`)).data
        } catch (error) {throw error as AxiosError}
    }
}
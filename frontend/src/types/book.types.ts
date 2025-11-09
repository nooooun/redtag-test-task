
export interface CreateBookDTO {
    title: string
    genre: string
    description: string
    publishDate: string
    authorFullName: string
}

export interface BookFilterParamsDTO {
    title?: string
    bookGenres?: string[]
    publishedBefore?: string
    publishedAfter?: string
    authorFullName?: string
}

export interface UpdateBookDetailsDTO {
    title?: string
    genre?: string
    description?: string
    publishDate?: string
}

export interface BookResponseDTO {
    id: number,
    title: string
    genre: string
    description: string
    publishDate: string
    authorFullName: string
}
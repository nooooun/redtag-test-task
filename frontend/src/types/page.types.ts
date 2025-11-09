export interface Page<T> {
    content: T[]
    totalPages: number
    totalElement: number
    number: number
    size: number
    first: Boolean
    last: Boolean
    empty: Boolean
}
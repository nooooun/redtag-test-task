export const BookGenres = {
    FANTASY: "Fantasy",
    SCIENCE_FICTION: "Science fiction",
    MYSTERY: "Mystery",
    HORROR: "Horror",
    BIOGRAPHY: "Biography",
    HISTORICAL_FICTION: "Historical fiction"
} as const

export type BookGenreKey = keyof typeof BookGenres
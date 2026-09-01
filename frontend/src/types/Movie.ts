export interface Movie {
    externalId: string;
    mediaType: "movie" | "tv";
    title: string;
    posterPath: string;
    year?: number;
}
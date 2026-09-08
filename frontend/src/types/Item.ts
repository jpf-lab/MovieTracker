export interface Item {
    externalId: string;
    mediaType: "movie" | "tv";
    title: string;
    posterPath: string;
    year?: number;
}
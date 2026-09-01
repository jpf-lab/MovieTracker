export interface SavedItem {
    id?: string;
    externalId: string;
    mediaType: "movie" | "tv";
    title: string;
    posterPath: string;
}
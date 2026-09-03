import axios from "axios";
import type { Movie } from "../types/Movie";

export type MovieFilters = {
    name?: string;
    mediaType?: "movie" | "tv";
    year?: number;
};

export const getFilteredMovies = (filters: MovieFilters) => {
    return axios.get<Movie[]>(import.meta.env.VITE_BACKEND_API + "/api/movies/filter", { params: filters });
};
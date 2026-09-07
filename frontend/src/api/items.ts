import axios from "axios";
import type {Item} from "../types/Item.ts";

export type ItemFilters = {
    name?: string;
    mediaType?: "movie" | "tv";
    year?: number;
};

export const getFilteredMovies = (filters: ItemFilters) => {
    return axios.get<Item[]>(import.meta.env.VITE_BACKEND_SEARCH, {
        params: {
            query:
                filters.name ? filters.name : "" +
                filters.mediaType ? " " + filters.mediaType : "" +
                filters.year ? " " + filters.year : ""
        }
    });
};

export const getRandomMovie = () => {
    return axios.get<Item>(import.meta.env.VITE_BACKEND_RANDOM);
};
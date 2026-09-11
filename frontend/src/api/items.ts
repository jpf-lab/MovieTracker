import axios from "axios";
import type {Item} from "../types/Item.ts";

export type ItemFilters = {
    name?: string;
    mediaType?: "movie" | "tv";
    year?: number;
};

export const getFilteredItems = (filters: ItemFilters) => {
    if (!filters.mediaType) {
        return axios.get<Item[]>(import.meta.env.VITE_BACKEND_MULTI_SEARCH, {
            params: {
                query:
                    filters.name ? filters.name : ""
            }
        });
    } else if (filters.mediaType === "movie") {
        return axios.get<Item[]>(import.meta.env.VITE_BACKEND_MOVIE_SEARCH, {
            params: {
                query: filters.name ? filters.name : undefined,
                year: filters.year ? filters.year : undefined
            }
        });
    } else if (filters.mediaType === "tv") {
        return axios.get<Item[]>(import.meta.env.VITE_BACKEND_TV_SEARCH, {
            params: {
                query: filters.name ? filters.name : undefined,
                year: filters.year ? filters.year : undefined
            }
        });
    } else {
        throw new Error("Wrong media type");
    }
};

export const getRandomMovie = () => {
    return axios.get<Item>(import.meta.env.VITE_BACKEND_RANDOM);
};
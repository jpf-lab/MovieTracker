import axios from "axios";
import type { SavedItem } from "../types/SavedItem";

const BASE_URL = "/api/saved";

export const saveItem = (item: SavedItem) => {
    return axios.post<SavedItem>(BASE_URL, item);
};

export const getSavedItems = () => {
    return axios.get<SavedItem[]>(BASE_URL);
};

export const isItemSaved = (externalId: string, mediaType: string) => {
    return axios.get<boolean>(`${BASE_URL}/exists`, {
        params: { externalId, mediaType },
    });
};

export const deleteSavedItem = (externalId: string, mediaType: string) => {
    return axios.delete(BASE_URL, {
        params: { externalId, mediaType },
    });
};
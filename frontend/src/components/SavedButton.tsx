import { useState } from "react";
import { FiHeart } from "react-icons/fi";
import { saveItem, deleteSavedItem } from "../api/savedItems";
import type { SavedItem } from "../types/SavedItem";

type SavedButtonProps = {
    item: Omit<SavedItem, "id">;
};

function SavedButton(props: SavedButtonProps) {
    const [isSaved, setIsSaved] = useState(false);
    const [isLoading, setIsLoading] = useState(false);

    function handleClick() {
        setIsLoading(true);

        if (isSaved) {
            deleteSavedItem(props.item.externalId, props.item.mediaType)
                .then(() => setIsSaved(false))
                .finally(() => setIsLoading(false));
        } else {
            saveItem(props.item as SavedItem)
                .then(() => setIsSaved(true))
                .finally(() => setIsLoading(false));
        }
    }

    return (
        <button
            className={`w-8.5 h-8.5 inline-flex items-center justify-center rounded-lg cursor-pointer transition-colors focus:outline-none focus-visible:ring-2 focus-visible:ring-cyan-400 ${
                isSaved
                    ? "bg-slate-800 ring-1 ring-cyan-400 text-cyan-400"
                    : "bg-slate-900 ring-1 ring-slate-700 text-slate-400 hover:text-slate-100 hover:ring-cyan-400"
            }`}
            onClick={handleClick}
            disabled={isLoading}
            title={isSaved ? "Aus Liste entfernen" : "Speichern"}
            type="button"
        >
            <FiHeart fill={isSaved ? "currentColor" : "none"} />
        </button>
    );
}

export default SavedButton;
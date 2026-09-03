import { useEffect, useState } from "react";
import { getSavedItems } from "../api/savedItems";
import type { SavedItem } from "../types/SavedItem";

function SavedItems() {
    const [items, setItems] = useState<SavedItem[]>([]);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        getSavedItems()
            .then((response) => {
                setItems(response.data);
                setError(null);
            })
            .catch(() => {
                setError("Liste konnte nicht geladen werden. Läuft das Backend?");
            })
            .finally(() => {
                setIsLoading(false);
            });
    }, []);

    if (isLoading) {
        return <p className="text-slate-400 p-6">Lädt...</p>;
    }

    if (error) {
        return <p className="text-rose-400 p-6">{error}</p>;
    }

    if (items.length === 0) {
        return <p className="text-slate-400 p-6">Noch nichts gespeichert.</p>;
    }

    return (
        <div className="p-6 bg-slate-950 min-h-screen">
            <h1 className="text-slate-100 text-xl font-medium mb-4">Meine Liste</h1>
            <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
                {items.map((item) => (
                    <div
                        key={item.id}
                        className="bg-slate-900 ring-1 ring-slate-800 rounded-xl overflow-hidden"
                    >
                        <div className="h-40 bg-slate-800 flex items-center justify-center text-slate-500">
                            Poster
                        </div>
                        <div className="p-3">
                            <p className="text-slate-100 text-sm font-medium">{item.title}</p>
                            <p className="text-slate-400 text-xs">
                                {item.mediaType === "movie" ? "Film" : "Serie"}
                            </p>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default SavedItems;
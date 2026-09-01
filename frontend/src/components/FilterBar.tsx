import { useState } from "react";
import type { MovieFilters } from "../api/movies";

type FilterBarProps = {
    onFilterChange: (filters: MovieFilters) => void;
};

function FilterBar(props: FilterBarProps) {
    const [name, setName] = useState("");
    const [mediaType, setMediaType] = useState<"movie" | "tv" | "">("");
    const [year, setYear] = useState("");

    function applyFilters() {
        props.onFilterChange({
            name: name || undefined,
            mediaType: mediaType || undefined,
            year: year ? Number(year) : undefined,
        });
    }

    function resetFilters() {
        setName("");
        setMediaType("");
        setYear("");
        props.onFilterChange({});
    }

    return (
        <div className="flex flex-wrap justify-center gap-3 mb-6">
            <input
                type="text"
                placeholder="Name"
                value={name}
                onChange={(e) => setName(e.target.value)}
                className="bg-slate-900 ring-1 ring-slate-800 rounded-lg px-3 py-2 text-sm text-slate-100 placeholder:text-slate-500 focus:outline-none focus:ring-cyan-400"
            />

            <select
                value={mediaType}
                onChange={(e) => setMediaType(e.target.value as "movie" | "tv" | "")}
                className="bg-slate-900 ring-1 ring-slate-800 rounded-lg px-3 py-2 text-sm text-slate-100 focus:outline-none focus:ring-cyan-400"
            >
                <option value="">Alle</option>
                <option value="movie">Filme</option>
                <option value="tv">Serien</option>
            </select>

            <input
                type="number"
                placeholder="Jahr"
                value={year}
                onChange={(e) => setYear(e.target.value)}
                className="bg-slate-900 ring-1 ring-slate-800 rounded-lg px-3 py-2 text-sm text-slate-100 placeholder:text-slate-500 focus:outline-none focus:ring-cyan-400 w-24"
            />

            <button
                type="button"
                onClick={applyFilters}
                className="bg-cyan-400 text-slate-950 rounded-lg px-4 py-2 text-sm font-medium hover:bg-cyan-300 transition-colors"
            >
                Filtern
            </button>

            <button
                type="button"
                onClick={resetFilters}
                className="bg-slate-900 ring-1 ring-slate-800 rounded-lg px-4 py-2 text-sm text-slate-400 hover:text-slate-100 transition-colors"
            >
                Zurücksetzen
            </button>
        </div>
    );
}

export default FilterBar;
import { useEffect, useState } from "react";
import { getFilteredMovies, getRandomMovie, type MovieFilters } from "../api/movies";
import type { Movie } from "../types/Movie";
import FilterBar from "./FilterBar";
import SavedButton from "./SavedButton";

function Home() {
    const [movies, setMovies] = useState<Movie[]>([]);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [isRandomLoading, setIsRandomLoading] = useState(false);

    function loadMovies(filters: MovieFilters) {
        setIsLoading(true);
        getFilteredMovies(filters)
            .then((response) => {
                setMovies(response.data);
                setError(null);
            })
            .catch(() => {
                setError("Filme konnten nicht geladen werden. Läuft das Backend?");
            })
            .finally(() => {
                setIsLoading(false);
            });
    }

    function loadRandomMovie() {
        setIsRandomLoading(true);
        getRandomMovie()
            .then((response) => {
                setMovies([response.data]);
                setError(null);
            })
            .catch(() => {
                setError("Zufälliger Titel konnte nicht geladen werden.");
            })
            .finally(() => {
                setIsRandomLoading(false);
            });
    }

    useEffect(() => {
        loadMovies({});
    }, []);

    return (
        <div className="min-h-screen bg-slate-950">
            <div className="max-w-5xl mx-auto p-6">
                <h1 className="text-slate-100 text-xl font-medium mb-4">MovieTracker</h1>

                <FilterBar onFilterChange={loadMovies} />

                <div className="flex justify-center mb-6">
                    <button
                        type="button"
                        onClick={loadRandomMovie}
                        disabled={isRandomLoading}
                        className="bg-cyan-400 text-slate-950 rounded-lg px-4 py-2 text-sm font-medium hover:bg-cyan-300 transition-colors disabled:opacity-50 animate-pulse"
                    >
                        {isRandomLoading ? "Lädt..." : "Überrasch mich"}
                    </button>
                </div>

                {isLoading && <p className="text-slate-400">Lädt...</p>}
                {error && <p className="text-rose-400">{error}</p>}

                {!isLoading && !error && movies.length === 0 && (
                    <p className="text-slate-400">Keine Treffer.</p>
                )}

                {!isLoading && !error && movies.length > 0 && (
                    <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
                        {movies.map((movie) => (
                            <div
                                key={movie.externalId}
                                className="bg-slate-900 ring-1 ring-slate-800 rounded-xl overflow-hidden"
                            >
                                <div className="h-40 bg-slate-800 flex items-center justify-center text-slate-500">
                                    Poster
                                </div>
                                <div className="p-3 flex justify-between items-start gap-2">
                                    <div>
                                        <p className="text-slate-100 text-sm font-medium">{movie.title}</p>
                                        <p className="text-slate-400 text-xs">
                                            {movie.year} · {movie.mediaType === "movie" ? "Film" : "Serie"}
                                        </p>
                                    </div>
                                    <SavedButton item={movie} />
                                </div>
                            </div>
                        ))}
                    </div>
                )}
            </div>
        </div>
    );
}

export default Home;
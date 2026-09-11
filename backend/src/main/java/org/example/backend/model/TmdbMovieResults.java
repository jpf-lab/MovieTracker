package org.example.backend.model;

import lombok.Builder;
import lombok.With;

import java.util.List;

@With
@Builder
public record TmdbMovieResults(
        Integer page,
        List<TmdbMovieResult> results,
        Integer total_pages,
        Integer total_results
) {
}

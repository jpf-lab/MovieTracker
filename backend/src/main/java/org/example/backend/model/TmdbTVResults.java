package org.example.backend.model;

import lombok.Builder;
import lombok.With;

import java.util.List;

@With
@Builder
public record TmdbTVResults(
        Integer page,
        List<TmdbTVResult> results,
        Integer total_pages,
        Integer total_results
) {
}

package org.example.backend.model;

import lombok.Builder;
import lombok.With;

import java.util.List;

@With
@Builder
public record TmdbResults(
        Integer page,
        List<TmdbResult> results,
        Integer total_pages,
        Integer total_results
) {
}

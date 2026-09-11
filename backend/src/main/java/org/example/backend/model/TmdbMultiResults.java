package org.example.backend.model;

import lombok.Builder;
import lombok.With;

import java.util.List;

@With
@Builder
public record TmdbMultiResults(
        Integer page,
        List<TmdbMultiResult> results,
        Integer total_pages,
        Integer total_results
) {
}

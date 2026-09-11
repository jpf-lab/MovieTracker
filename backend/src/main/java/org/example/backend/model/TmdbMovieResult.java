package org.example.backend.model;

import lombok.Builder;
import lombok.With;

import java.util.List;

@With
@Builder
public record TmdbMovieResult(
        Integer id,
        String poster_path,
        String overview,

        //Movie exclusive
        String title,
        String original_title,
        String release_date,

        List<Integer> genre_ids


) {
}

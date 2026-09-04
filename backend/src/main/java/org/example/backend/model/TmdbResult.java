package org.example.backend.model;

import lombok.Builder;
import lombok.With;

import java.util.List;

@With
@Builder
public record TmdbResult(
        Integer id,
        String media_type,
        String poster_path,
        String overview,
        String release_date,

        //TV exclusive
        String name,
        String original_name,
        //Movie exclusive
        String title,
        String original_title,

        List<Integer> genre_ids
) {
}

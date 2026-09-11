package org.example.backend.model;

import lombok.Builder;
import lombok.With;

import java.util.List;

@With
@Builder
public record TmdbMultiResult(
        Integer id,
        String media_type,
        String poster_path,
        String overview,

        //TV exclusive
        String name,
        String original_name,
        String first_air_date,
        //Movie exclusive
        String title,
        String original_title,
        String release_date,

        List<Integer> genre_ids
) {
}

package org.example.backend.model;

import lombok.Builder;
import lombok.With;

import java.util.List;

@With
@Builder
public record TmdbTVResult(
        Integer id,
        String poster_path,
        String overview,

        //TV exclusive
        String name,
        String original_name,
        String first_air_date,

        List<Integer> genre_ids
) {
}

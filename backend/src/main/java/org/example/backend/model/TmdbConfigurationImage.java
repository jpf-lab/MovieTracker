package org.example.backend.model;

import lombok.Builder;
import lombok.With;

@With
@Builder
public record TmdbConfigurationImage(
        String base_url,
        String secure_base_url,
        String[] backdrop_sizes,
        String[] logo_sizes,
        String[] poster_sizes,
        String[] profile_sizes,
        String[] still_sizes
) {
}

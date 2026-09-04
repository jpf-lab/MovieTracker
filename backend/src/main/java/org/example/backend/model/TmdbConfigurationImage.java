package org.example.backend.model;

import lombok.Builder;
import lombok.With;

import java.util.List;

@With
@Builder
public record TmdbConfigurationImage(
        String base_url,
        String secure_base_url,
        List<String> backdrop_sizes,
        List<String> logo_sizes,
        List<String> poster_sizes,
        List<String> profile_sizes,
        List<String> still_sizes
) {
}

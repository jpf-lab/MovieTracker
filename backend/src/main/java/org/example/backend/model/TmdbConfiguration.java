package org.example.backend.model;

import lombok.Builder;
import lombok.With;

@With
@Builder
public record TmdbConfiguration(
        TmdbConfigurationImage images,
        String[] change_keys
) {
}

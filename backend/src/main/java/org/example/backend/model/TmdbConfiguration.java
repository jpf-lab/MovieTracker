package org.example.backend.model;

import lombok.Builder;
import lombok.With;

import java.util.List;

@With
@Builder
public record TmdbConfiguration(
        TmdbConfigurationImage images,
        List<String> change_keys
) {
}

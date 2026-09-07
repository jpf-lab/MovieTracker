package org.example.backend.dto;

import lombok.Builder;
import lombok.With;

@With
@Builder
public record ItemDTO(
        String externalId,
        String mediaType,
        String title,
        String posterPath,
        Integer year
) {
}


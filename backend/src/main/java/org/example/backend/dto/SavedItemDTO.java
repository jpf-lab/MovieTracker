package org.example.backend.dto;

import lombok.Builder;
import lombok.With;

@With
@Builder
public record SavedItemDTO(

        String externalId,
        String mediaType,
        String title,
        String posterPath
) {}
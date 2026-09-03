package org.example.backend.model;

import lombok.Builder;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@With
@Builder
@Document(collection = "saved_items")
public record SavedItem(
        @Id
        String id,
        String externalId,
        String mediaType,
        String title,
        String posterPath
) {}
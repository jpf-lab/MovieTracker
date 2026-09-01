package org.example.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "saved_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SavedItem {

    @Id
    private String id;
    private String externalId;
    private String mediaType;
    private String title;
    private String posterPath;
}
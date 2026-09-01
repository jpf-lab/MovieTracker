package org.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {

    private String externalId;
    private String mediaType;
    private String title;
    private String posterPath;
    private Integer year;
}


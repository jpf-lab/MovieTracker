package org.example.backend.service;

import org.example.backend.dto.MovieDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlaceholderMovieProvider implements MovieProvider {

    @Override
    public List<MovieDTO> search(String name, String mediaType, Integer year) {
        return List.of(
                new MovieDTO("550", "movie", "Fight Club", "/poster1.jpg", 1999),
                new MovieDTO("1396", "tv", "Breaking Bad", "/poster2.jpg", 2008),
                new MovieDTO("157336", "movie", "Interstellar", "/poster3.jpg", 2014),
                new MovieDTO("1408", "tv", "Severance", "/poster4.jpg", 2022)
        );
    }
}
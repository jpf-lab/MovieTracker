package org.example.backend.service;

import org.example.backend.dto.MovieDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlaceholderMovieProvider implements MovieProvider {

    @Override
    public List<MovieDto> search(String name, String mediaType, Integer year) {
        return List.of(
                new MovieDto("550", "movie", "Fight Club", "/poster1.jpg", 1999),
                new MovieDto("1396", "tv", "Breaking Bad", "/poster2.jpg", 2008),
                new MovieDto("157336", "movie", "Interstellar", "/poster3.jpg", 2014),
                new MovieDto("1408", "tv", "Severance", "/poster4.jpg", 2022)
        );
    }
}
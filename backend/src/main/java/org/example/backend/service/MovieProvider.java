package org.example.backend.service;

import org.example.backend.dto.MovieDto;

import java.util.List;

public interface MovieProvider {
    List<MovieDto> search(String name, String mediaType, Integer year);
}

package org.example.backend.service;

import org.example.backend.dto.MovieDTO;

import java.util.List;

public interface MovieProvider {
    List<MovieDTO> search(String name, String mediaType, Integer year);
}

package org.example.backend.service;

import lombok.RequiredArgsConstructor;
import org.example.backend.dto.MovieDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilterService {

    private final MovieProvider movieProvider;

    public List<MovieDTO> filter(String name, String mediaType, Integer year) {
        return movieProvider.search(name, mediaType, year);
    }
}

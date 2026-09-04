package org.example.backend.service;

import lombok.RequiredArgsConstructor;
import org.example.backend.dto.MovieDTO;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RandomService {

    private final MovieProvider movieProvider;

    public MovieDTO getRandom() {
        return movieProvider.getRandom();
    }
}
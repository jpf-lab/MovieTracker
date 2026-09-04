package org.example.backend.service;

import org.example.backend.dto.MovieDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class RandomServiceTest {

    @Mock
    private MovieProvider movieProvider;

    private RandomService randomService;

    @BeforeEach
    void setUp() {
        // Given: der Mock wird vor jedem Test frisch initialisiert
        MockitoAnnotations.openMocks(this);
        randomService = new RandomService(movieProvider);
    }

    @Test
    void getRandom_shouldReturnMovieFromProvider() {
        // Given: der (gemockte) Provider liefert einen festen Film
        MovieDTO movie = new MovieDTO("550", "movie", "Fight Club", "/poster1.jpg", 1999);
        when(movieProvider.getRandom()).thenReturn(movie);

        // When: der Service wird aufgerufen
        MovieDTO result = randomService.getRandom();

        // Then: das Ergebnis entspricht genau dem, was der Provider
        // geliefert hat - der Service reicht nur durch
        assertEquals(movie, result);
    }
}
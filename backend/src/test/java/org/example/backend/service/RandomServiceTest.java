package org.example.backend.service;

import org.example.backend.dto.ItemDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class RandomServiceTest {

    @Mock
    private ItemProvider itemProvider;

    private RandomService randomService;

    @BeforeEach
    void setUp() {
        // Given: der Mock wird vor jedem Test frisch initialisiert
        MockitoAnnotations.openMocks(this);
        randomService = new RandomService(itemProvider);
    }

    @Test
    void getRandom_shouldReturnMovieFromProvider() {
        // Given: der (gemockte) Provider liefert einen festen Film
        ItemDTO movie = new ItemDTO("550", "movie", "Fight Club", "/poster1.jpg", 1999);
        when(itemProvider.getRandom()).thenReturn(movie);

        // When: der Service wird aufgerufen
        ItemDTO result = randomService.getRandom();

        // Then: das Ergebnis entspricht genau dem, was der Provider
        // geliefert hat - der Service reicht nur durch
        assertEquals(movie, result);
    }
}
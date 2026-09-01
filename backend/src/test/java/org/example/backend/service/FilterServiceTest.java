package org.example.backend.service;

import org.example.backend.dto.MovieDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FilterServiceTest {

    @Mock
    private MovieProvider movieProvider;

    private FilterService filterService;

    @BeforeEach
    void setUp() {
        // Given: der Mock wird vor jedem Test frisch initialisiert
        MockitoAnnotations.openMocks(this);
        filterService = new FilterService(movieProvider);
    }

    @Test
    void filter_shouldReturnResultsFromProvider() {
        // Given: der (gemockte) Provider liefert eine feste Liste
        // zurueck, wenn genau diese Filterparameter reinkommen
        MovieDto movie = new MovieDto("550", "movie", "Fight Club", "/poster1.jpg", 1999);
        when(movieProvider.search("Fight", "movie", 1999)).thenReturn(List.of(movie));

        // When: der Service wird mit denselben Parametern aufgerufen
        List<MovieDto> result = filterService.filter("Fight", "movie", 1999);

        // Then: das Ergebnis entspricht genau dem, was der Provider
        // zurueckgegeben hat - der Service reicht nur durch
        assertEquals(1, result.size());
        assertEquals(movie, result.get(0));
    }

    @Test
    void filter_shouldDelegateToProviderWithSameParameters() {
        // Given: keine Vorbereitung noetig, wir pruefen nur die
        // Weiterleitung der Parameter

        // When: der Service wird mit bestimmten Filterwerten aufgerufen
        filterService.filter("Dark", "tv", 2017);

        // Then: der Provider wurde exakt mit denselben Parametern
        // aufgerufen - bestaetigt, dass der Service nur delegiert
        // (SRP: FilterService hat keine eigene Filterlogik, das
        // macht der Provider)
        verify(movieProvider).search("Dark", "tv", 2017);
    }
}

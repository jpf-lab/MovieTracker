package org.example.backend.service;

import org.example.backend.dto.MovieDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

class PlaceholderMovieProviderTest {

    @Test
    void search_shouldReturnNonEmptyPlaceholderList() {
        // Given
        PlaceholderMovieProvider provider = new PlaceholderMovieProvider();

        // When
        List<MovieDto> result = provider.search("egal", "egal", 2000);

        // Then: nur pruefen, dass ueberhaupt Platzhalterdaten
        // zurueckkommen - mehr gibt es hier nicht zu testen,
        // da die Klasse ohnehin temporaer ist
        assertFalse(result.isEmpty());
    }
}

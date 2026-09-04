package org.example.backend.service;

import org.example.backend.dto.MovieDTO;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TmdbMovieProviderTest {

    private final TmdbMovieProvider provider = new TmdbMovieProvider(RestClient.builder());

    @Test
    void mapResultsToDto_shouldMapMovieCorrectly() {
        // Given: eine TMDB-Antwort im rohen JSON-Format (als Map),
        // wie sie von /search/movie zurueckkommen wuerde
        Map<String, Object> response = Map.of(
                "results", List.of(
                        Map.of(
                                "id", 550,
                                "title", "Fight Club",
                                "poster_path", "/poster1.jpg",
                                "release_date", "1999-10-15"
                        )
                )
        );

        // When: die Mapping-Methode wird mit "movie" als Fallback
        // aufgerufen (weil TMDB bei /search/movie kein eigenes
        // media_type-Feld mitschickt)
        List<MovieDTO> result = provider.mapResultsToDto(response, "movie");

        // Then: alle Felder wurden korrekt uebernommen, das Jahr
        // wurde aus release_date extrahiert
        assertEquals(1, result.size());
        assertEquals("550", result.get(0).externalId());
        assertEquals("movie", result.get(0).mediaType());
        assertEquals("Fight Club", result.get(0).title());
        assertEquals("/poster1.jpg", result.get(0).posterPath());
        assertEquals(1999, result.get(0).year());
    }

    @Test
    void mapResultsToDto_shouldMapTvShowCorrectly() {
        // Given: eine TMDB-Antwort fuer eine Serie - hier heisst
        // das Titelfeld "name" statt "title", das Datumsfeld
        // "first_air_date" statt "release_date"
        Map<String, Object> response = Map.of(
                "results", List.of(
                        Map.of(
                                "id", 1396,
                                "name", "Breaking Bad",
                                "poster_path", "/poster2.jpg",
                                "first_air_date", "2008-01-20"
                        )
                )
        );

        // When
        List<MovieDTO> result = provider.mapResultsToDto(response, "tv");

        // Then
        assertEquals("Breaking Bad", result.get(0).title());
        assertEquals(2008, result.get(0).year());
        assertEquals("tv", result.get(0).mediaType());
    }

    @Test
    void mapResultsToDto_shouldReturnNullYear_whenDateIsMissing() {
        // Given: ein Ergebnis ohne Erscheinungsdatum (kommt bei
        // TMDB gelegentlich vor, z.B. bei noch nicht veroeffentlichten
        // Titeln)
        Map<String, Object> response = Map.of(
                "results", List.of(
                        Map.of(
                                "id", 999,
                                "title", "Unreleased Movie",
                                "poster_path", "/poster3.jpg",
                                "release_date", ""
                        )
                )
        );

        // When
        List<MovieDTO> result = provider.mapResultsToDto(response, "movie");

        // Then: kein Absturz, Jahr ist einfach null
        assertNull(result.get(0).year());
    }

    @Test
    void mapResultsToDto_shouldUseMediaTypeFromResponse_whenPresent() {
        // Given: eine Antwort wie von /trending/all/day, die pro
        // Ergebnis ein eigenes media_type-Feld mitliefert (statt
        // eines einheitlichen Fallback-Typs)
        Map<String, Object> response = Map.of(
                "results", List.of(
                        Map.of(
                                "id", 1,
                                "media_type", "tv",
                                "name", "Severance",
                                "poster_path", "/poster4.jpg",
                                "first_air_date", "2022-02-18"
                        )
                )
        );

        // When: fallbackMediaType wird absichtlich ignoriert, weil
        // die Antwort ihr eigenes media_type mitbringt
        List<MovieDTO> result = provider.mapResultsToDto(response, null);

        // Then
        assertEquals("tv", result.get(0).mediaType());
        assertEquals("Severance", result.get(0).title());
    }
}

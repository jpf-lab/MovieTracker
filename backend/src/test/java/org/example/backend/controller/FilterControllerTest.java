package org.example.backend.controller;

import org.example.backend.dto.MovieDto;
import org.example.backend.service.FilterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FilterController.class)
class FilterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FilterService filterService;

    @Test
    void filter_shouldReturnMatchingMovies() throws Exception {
        // Given: der (gemockte) Service liefert ein Ergebnis fuer
        // genau diese drei Filterparameter
        MovieDto movie = new MovieDto("550", "movie", "Fight Club", "/poster1.jpg", 1999);
        when(filterService.filter("Fight", "movie", 1999)).thenReturn(List.of(movie));

        // When: ein GET-Request mit allen drei Query-Parametern
        // wird simuliert
        // Then: die Antwort enthaelt den erwarteten Titel
        mockMvc.perform(get("/api/movies/filter")
                        .param("name", "Fight")
                        .param("mediaType", "movie")
                        .param("year", "1999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Fight Club"));
    }

    @Test
    void filter_shouldWorkWithoutAnyParameters() throws Exception {
        // Given: der Service liefert etwas zurueck, auch wenn
        // alle Parameter fehlen (sie sind ja optional)
        MovieDto movie = new MovieDto("550", "movie", "Fight Club", "/poster1.jpg", 1999);
        when(filterService.filter(null, null, null)).thenReturn(List.of(movie));

        // When: ein GET-Request ganz ohne Query-Parameter
        // Then: trotzdem Status 200, kein Fehler wegen fehlender
        // Parameter (bestaetigt "required = false" im Controller)
        mockMvc.perform(get("/api/movies/filter"))
                .andExpect(status().isOk());
    }
}
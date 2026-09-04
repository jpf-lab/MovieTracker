package org.example.backend.controller;

import org.example.backend.dto.MovieDTO;
import org.example.backend.service.RandomService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RandomController.class)
class RandomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RandomService randomService;

    @Test
    void getRandom_shouldReturnMovie() throws Exception {
        // Given: der (gemockte) Service liefert einen festen Film
        MovieDTO movie = new MovieDTO("550", "movie", "Fight Club", "/poster1.jpg", 1999);
        when(randomService.getRandom()).thenReturn(movie);

        // When: ein GET-Request an /api/movies/random wird simuliert
        // Then: die Antwort enthaelt den erwarteten Titel
        mockMvc.perform(get("/api/movies/random"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Fight Club"));
    }
}
package org.example.backend.controller;

import org.example.backend.dto.SavedItemDTO;
import org.example.backend.model.SavedItem;
import org.example.backend.service.SavedItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SavedItemController.class)
class SavedItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SavedItemService savedItemService;

    @Test
    void save_shouldReturnSavedItem() throws Exception {
        // Given: der Request-Body ist ein DTO-foermiges JSON (ohne id),
        // der (gemockte) Service liefert die vollstaendige Entity
        // (mit generierter id) zurueck
        SavedItem savedEntity = new SavedItem("1", "123", "movie", "Test Movie", "/poster.jpg");
        when(savedItemService.save(any(SavedItemDTO.class))).thenReturn(savedEntity);

        String requestBody = """
                {
                  "externalId": "123",
                  "mediaType": "movie",
                  "title": "Test Movie",
                  "posterPath": "/poster.jpg"
                }
                """;

        // When: ein POST-Request mit diesem JSON wird simuliert
        // Then: die Antwort enthaelt die id und den Titel
        mockMvc.perform(post("/api/saved")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Test Movie"));
    }

    @Test
    void findAll_shouldReturnList() throws Exception {
        // Given: der Service liefert eine Liste mit einem Item
        SavedItem item = new SavedItem("1", "123", "movie", "Test Movie", "/poster.jpg");
        when(savedItemService.findAll()).thenReturn(List.of(item));

        // When: ein GET-Request wird simuliert
        // Then: die Antwort enthaelt das Item
        mockMvc.perform(get("/api/saved"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Movie"));
    }

    @Test
    void isSaved_shouldReturnTrue() throws Exception {
        when(savedItemService.isSaved("123", "movie")).thenReturn(true);

        mockMvc.perform(get("/api/saved/exists")
                        .param("externalId", "123")
                        .param("mediaType", "movie"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void delete_shouldCallService() throws Exception {
        mockMvc.perform(delete("/api/saved")
                        .param("externalId", "123")
                        .param("mediaType", "movie"))
                .andExpect(status().isOk());

        verify(savedItemService).deleteByExternalIdAndMediaType("123", "movie");
    }
}
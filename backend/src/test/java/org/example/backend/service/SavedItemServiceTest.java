package org.example.backend.service;

import org.example.backend.model.SavedItem;
import org.example.backend.repository.SavedItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SavedItemServiceTest {

    @Mock
    private SavedItemRepository savedItemRepository;

    private SavedItemService savedItemService;

    @BeforeEach
    void setUp() {
        // Given: der Mock wird vor JEDEM Test frisch initialisiert,
        // damit sich Tests nicht gegenseitig beeinflussen
        MockitoAnnotations.openMocks(this);
        savedItemService = new SavedItemService(savedItemRepository);
    }

    @Test
    void save_shouldCallRepositorySave() {
        // Given: ein Beispiel-Item und die Erwartung, was das
        // (gemockte) Repository beim Speichern zurueckgibt
        SavedItem item = new SavedItem("1", "123", "movie", "Test Movie", "/poster.jpg");
        when(savedItemRepository.save(item)).thenReturn(item);

        // When: die zu testende Methode wird aufgerufen
        SavedItem result = savedItemService.save(item);

        // Then: pruefen, dass das Ergebnis stimmt UND dass das
        // Repository tatsaechlich mit dem richtigen Item aufgerufen wurde
        assertEquals(item, result);
        verify(savedItemRepository).save(item);
    }

    @Test
    void findAll_shouldReturnAllItems() {
        // Given: das Repository "kennt" ein Item
        SavedItem item = new SavedItem("1", "123", "movie", "Test Movie", "/poster.jpg");
        when(savedItemRepository.findAll()).thenReturn(List.of(item));

        // When: findAll() wird im Service aufgerufen
        List<SavedItem> result = savedItemService.findAll();

        // Then: die Liste enthaelt genau das erwartete Item
        assertEquals(1, result.size());
        assertEquals(item, result.get(0));
    }

    @Test
    void isSaved_shouldReturnTrue_whenItemExists() {
        // Given: das Repository meldet, dass der Eintrag existiert
        when(savedItemRepository.existsByExternalIdAndMediaType("123", "movie")).thenReturn(true);

        // When: isSaved() wird aufgerufen
        boolean result = savedItemService.isSaved("123", "movie");

        // Then: der Service gibt true weiter
        assertTrue(result);
    }

    @Test
    void deleteByExternalIdAndMediaType_shouldCallRepositoryDelete() {
        // Given: keine Vorbereitung noetig, delete hat keinen Rueckgabewert

        // When: die Loesch-Methode wird aufgerufen
        savedItemService.deleteByExternalIdAndMediaType("123", "movie");

        // Then: pruefen, dass das Repository mit den richtigen
        // Parametern aufgerufen wurde (da es nichts zurueckgibt,
        // ist verify() hier die einzige Moeglichkeit zu testen)
        verify(savedItemRepository).deleteByExternalIdAndMediaType("123", "movie");
    }
}
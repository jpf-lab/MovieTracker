package org.example.backend.service;

import lombok.RequiredArgsConstructor;
import org.example.backend.model.SavedItem;
import org.example.backend.repository.SavedItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SavedItemService {

    private final SavedItemRepository savedItemRepository;

    public SavedItem save(SavedItem item) {
        return savedItemRepository.save(item);
    }

    public List<SavedItem> findAll() {
        return savedItemRepository.findAll();
    }

    public boolean isSaved(String externalId, String mediaType) {
        return savedItemRepository.existsByExternalIdAndMediaType(externalId, mediaType);
    }

    public void deleteByExternalIdAndMediaType(String externalId, String mediaType) {
        savedItemRepository.deleteByExternalIdAndMediaType(externalId, mediaType);
    }
}
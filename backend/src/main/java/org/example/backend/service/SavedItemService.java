package org.example.backend.service;

import lombok.RequiredArgsConstructor;
import org.example.backend.dto.SavedItemDTO;
import org.example.backend.model.SavedItem;
import org.example.backend.repository.SavedItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SavedItemService {

    private final SavedItemRepository savedItemRepository;

    public SavedItem save(SavedItemDTO item) {
        return savedItemRepository.save(SavedItem.builder()
                .mediaType(item.mediaType())
                .externalId(item.externalId())
                .posterPath(item.posterPath())
                .title(item.title())
                .build());
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
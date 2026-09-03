package org.example.backend.repository;

import org.example.backend.model.SavedItem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SavedItemRepository extends MongoRepository<SavedItem, String> {

    boolean existsByExternalIdAndMediaType(String externalId, String mediaType);

    void deleteByExternalIdAndMediaType(String externalId, String mediaType);
}
package org.example.backend.controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.dto.SavedItemDTO;
import org.example.backend.model.SavedItem;
import org.example.backend.service.SavedItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/saved")
@RequiredArgsConstructor
public class SavedItemController {

    private final SavedItemService savedItemService;

    @PostMapping
    public SavedItem save(@RequestBody SavedItemDTO savedItemDTO) {

        return savedItemService.save(savedItemDTO);
    }

    @GetMapping
    public List<SavedItem> findAll() {
        return savedItemService.findAll();
    }

    @GetMapping("/exists")
    public boolean isSaved(@RequestParam String externalId, @RequestParam String mediaType) {
        return savedItemService.isSaved(externalId, mediaType);
    }

    @DeleteMapping
    public void delete(@RequestParam String externalId, @RequestParam String mediaType) {
        savedItemService.deleteByExternalIdAndMediaType(externalId, mediaType);
    }
}
package org.example.backend.service;

import lombok.RequiredArgsConstructor;
import org.example.backend.dto.ItemDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilterService {

    private final ItemProvider itemProvider;

    public List<ItemDTO> filter(String name, String mediaType, Integer year) {
        return itemProvider.search(name, mediaType, year);
    }
}

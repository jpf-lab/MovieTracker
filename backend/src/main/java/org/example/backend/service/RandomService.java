package org.example.backend.service;

import lombok.RequiredArgsConstructor;
import org.example.backend.dto.ItemDTO;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RandomService {

    private final ItemProvider itemProvider;

    public ItemDTO getRandom() {
        return itemProvider.getRandom();
    }
}
package org.example.backend.service;

import org.example.backend.dto.ItemDTO;

import java.util.List;

public interface ItemProvider {
    List<ItemDTO> search(String name, String mediaType, Integer year);

    ItemDTO getRandom();
}

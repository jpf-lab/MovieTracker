package org.example.backend.controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.dto.ItemDTO;
import org.example.backend.service.RandomService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RandomController {

    private final RandomService randomService;

    @GetMapping("/random")
    public ItemDTO getRandom() {
        return randomService.getRandom();
    }
}

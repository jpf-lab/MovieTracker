package org.example.backend.controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.dto.MovieDTO;
import org.example.backend.service.RandomService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class RandomController {

    private final RandomService randomService;

    @GetMapping("/random")
    public MovieDTO getRandom() {
        return randomService.getRandom();
    }
}

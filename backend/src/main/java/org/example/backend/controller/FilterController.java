package org.example.backend.controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.dto.MovieDTO;
import org.example.backend.service.FilterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class FilterController {

    private final FilterService filterService;

    @GetMapping("/filter")
    public List<MovieDTO> filter(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String mediaType,
            @RequestParam(required = false) Integer year
    ) {
        return filterService.filter(name, mediaType, year);
    }
}


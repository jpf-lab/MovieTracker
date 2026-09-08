package org.example.backend.controller;

import org.example.backend.dto.ItemDTO;
import org.example.backend.model.TmdbConfiguration;
import org.example.backend.service.TmdbService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
public class TrackerController {

    private final TmdbService tmdbService;

    public TrackerController(TmdbService tmdbService) {
        this.tmdbService = tmdbService;
    }


    @GetMapping("/search")
    public List<ItemDTO> findByQuery(@RequestParam String query) {
        TmdbConfiguration configuration = tmdbService.getConfiguration();

        return tmdbService.findByQuery(query, 1, configuration);
    }
}

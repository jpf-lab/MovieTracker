package org.example.backend.controller;

import org.example.backend.dto.ItemDTO;
import org.example.backend.model.*;
import org.example.backend.service.TmdbService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
        TmdbMultiResults results = tmdbService.findByQuery(query, 1);
        TmdbConfiguration configuration = tmdbService.getConfiguration();

        return tmdbService.generateItemDtoList(results, configuration);
    }

    @GetMapping("/search/movie")
    public List<ItemDTO> findMovie(@RequestParam String query, @RequestParam String year) {
        TmdbMovieResults results = tmdbService.findMovie(query, 1, year);
        TmdbConfiguration configuration = tmdbService.getConfiguration();

        List<TmdbMultiResult> resultList = new ArrayList<>(List.of());

        for (TmdbMovieResult r : results.results()) {
            resultList.add(new TmdbMultiResult(
                    r.id(),
                    "tv",
                    r.poster_path(),
                    r.overview(),
                    "",
                    "",
                    "",
                    r.title(),
                    r.original_title(),
                    r.release_date(),
                    r.genre_ids()
            ));
        }
        TmdbMultiResults multiResults = new TmdbMultiResults(
                results.page(),
                resultList,
                results.total_pages(),
                results.total_results()
        );
        return tmdbService.generateItemDtoList(multiResults, configuration);
    }

    @GetMapping("/search/tv")
    public List<ItemDTO> findTV(@RequestParam String query, @RequestParam String year) {
        TmdbTVResults results = tmdbService.findTV(query, 1, year);
        TmdbConfiguration configuration = tmdbService.getConfiguration();

        List<TmdbMultiResult> resultList = new ArrayList<>(List.of());

        for (TmdbTVResult r : results.results()) {
            resultList.add(new TmdbMultiResult(
                    r.id(),
                    "tv",
                    r.poster_path(),
                    r.overview(),
                    r.name(),
                    r.original_name(),
                    r.first_air_date(),
                    "",
                    "",
                    "",
                    r.genre_ids()
            ));
        }
        TmdbMultiResults multiResults = new TmdbMultiResults(
                results.page(),
                resultList,
                results.total_pages(),
                results.total_results()
        );

        return tmdbService.generateItemDtoList(multiResults, configuration);
    }
}

package org.example.backend.controller;

import org.example.backend.model.TmdbConfiguration;
import org.example.backend.model.TmdbResult;
import org.example.backend.model.TmdbResults;
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

    private String getPosterWidth(String[] posterSizes){
        if(posterSizes.length>3){
            return posterSizes[3];
        }else{
            return posterSizes[posterSizes.length-1];
        }
    }

    @GetMapping("/search")
    public TmdbResults findByQuery(@RequestParam String query) {
        TmdbResults results = tmdbService.findByQuery(query, 1);
        TmdbConfiguration configuration = tmdbService.getConfiguration();

        List<TmdbResult> resultsWithPosterPath = results.results()
                .stream()
                .map(r -> new TmdbResult(
                        r.id(),
                        r.media_type(),
                        configuration.images().secure_base_url()
                                + getPosterWidth(configuration.images().poster_sizes())
                                + r.poster_path(),
                        r.overview(),
                        r.release_date(),

                        //TV exclusive
                        r.name(),
                        r.original_name(),
                        //Movie exclusive
                        r.title(),
                        r.original_title(),

                        r.genre_ids()
                ))
                .toList();

        return new TmdbResults(
                results.page(),
                resultsWithPosterPath,
                results.total_pages(),
                results.total_results()
        );
    }
}

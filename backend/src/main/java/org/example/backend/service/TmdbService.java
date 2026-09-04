package org.example.backend.service;

import org.example.backend.model.TmdbConfiguration;
import org.example.backend.model.TmdbResults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class TmdbService {

    private final RestClient restClient;

    @Value("${tmdb.settings.token}")
    String tmdbSettingsToken;

//    @Value("${tmdb.settings.key}")
//    String tmdbSettingsKey;

    @Value("${tmdb.settings.language}")
    String tmdbSettingsLanguage;

    @Value("${tmdb.settings.include_adult}")
    String tmdbSettingsIncludeAdults;


    public TmdbService(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder
                .baseUrl("https://api.themoviedb.org/3")
                .build();
    }

    public TmdbResults findByQuery(String query, Integer page) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search/multi")
                        .queryParam("query", query)
                        .queryParam("include_adult", tmdbSettingsIncludeAdults)
                        .queryParam("language", tmdbSettingsLanguage)
                        .queryParam("page", page)
                        .build())
                .header("Authorization", "Bearer " + tmdbSettingsToken)
                .retrieve()
                .body(TmdbResults.class);
    }

    public TmdbConfiguration getConfiguration() {
        return restClient.get()
                .uri("/configuration")
                .header("Authorization", "Bearer " + tmdbSettingsToken)
                .retrieve()
                .body(TmdbConfiguration.class);
    }
}

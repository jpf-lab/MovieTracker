package org.example.backend.service;

import org.example.backend.dto.ItemDTO;
import org.example.backend.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

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


    private String getPosterWidth(List<String> posterSizes) {
        String posterWidth = "original";
        for (String posterSize : posterSizes) {
            if (posterSize.startsWith("w3")) {
                posterWidth = posterSize;
                break;
            }
        }
        return posterWidth;
    }

    private String getPosterPath(TmdbConfiguration configuration, String posterPath) {
        return configuration.images().secure_base_url()
                + getPosterWidth(configuration.images().poster_sizes())
                + posterPath;
    }

    private Integer getYear(String itemDate) {
//        String itemDate = result.media_type().equals("tv") ? result.first_air_date() : result.release_date();

        return (itemDate != null && !itemDate.isBlank()) ? Integer.parseInt(itemDate.substring(0, 4)) : null;
    }

    public TmdbMultiResults findByQuery(String query, Integer page) {
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
                .body(TmdbMultiResults.class);
    }

    public TmdbMovieResults findMovie(String query, Integer page, String year) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search/movie")
                        .queryParam("query", query)
                        .queryParam("include_adult", tmdbSettingsIncludeAdults)
                        .queryParam("language", tmdbSettingsLanguage)
                        .queryParam("page", page)
                        .queryParam("primary_release_year", year)
                        .build())
                .header("Authorization", "Bearer " + tmdbSettingsToken)
                .retrieve()
                .body(TmdbMovieResults.class);
    }

    public TmdbTVResults findTV(String query, Integer page, String year) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search/tv")
                        .queryParam("query", query)
                        .queryParam("include_adult", tmdbSettingsIncludeAdults)
                        .queryParam("language", tmdbSettingsLanguage)
                        .queryParam("page", page)
                        .queryParam("first_air_date_year", year)
                        .build())
                .header("Authorization", "Bearer " + tmdbSettingsToken)
                .retrieve()
                .body(TmdbTVResults.class);
    }

    public List<ItemDTO> generateItemDtoList(TmdbMultiResults results, TmdbConfiguration configuration) {
        return results != null && !results.results().isEmpty() ?
                results.results().stream()
                        .map(result -> new ItemDTO(
                                        result.id().toString(),
                                        result.media_type(),
                                        result.media_type().equals("tv") ? result.name() : result.title(),
                                        getPosterPath(configuration, result.poster_path()),
                                        getYear(result.media_type().equals("tv") ? result.first_air_date() : result.release_date())
                                )
                        )
                        .toList()
                : null;
    }

    public TmdbConfiguration getConfiguration() {
        return restClient.get()
                .uri("/configuration")
                .header("Authorization", "Bearer " + tmdbSettingsToken)
                .retrieve()
                .body(TmdbConfiguration.class);
    }
}

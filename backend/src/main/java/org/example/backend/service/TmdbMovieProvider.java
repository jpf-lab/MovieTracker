package org.example.backend.service;

import org.example.backend.dto.MovieDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class TmdbMovieProvider implements MovieProvider {

    private final RestClient restClient;

    @Value("${tmdb.settings.token}")
    private String tmdbToken;

    @Value("${tmdb.settings.language}")
    private String tmdbLanguage;

    public TmdbMovieProvider(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder
                .baseUrl("https://api.themoviedb.org/3")
                .build();
    }

    @Override
    public List<MovieDTO> search(String name, String mediaType, Integer year) {
        String endpoint = (mediaType != null && mediaType.equals("tv")) ? "/search/tv" : "/search/movie";

        Map<String, Object> response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(endpoint)
                        .queryParam("query", name != null ? name : "")
                        .queryParam("language", tmdbLanguage)
                        .queryParamIfPresent("year", java.util.Optional.ofNullable(year))
                        .build())
                .header("Authorization", "Bearer " + tmdbToken)
                .retrieve()
                .body(Map.class);

        return mapResultsToDto(response, mediaType != null ? mediaType : "movie");
    }

    @Override
    public MovieDTO getRandom() {
        Map<String, Object> response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/trending/all/day")
                        .queryParam("language", tmdbLanguage)
                        .build())
                .header("Authorization", "Bearer " + tmdbToken)
                .retrieve()
                .body(Map.class);

        List<MovieDTO> movies = mapResultsToDto(response, null);
        int index = ThreadLocalRandom.current().nextInt(movies.size());
        return movies.get(index);
    }

    @SuppressWarnings("unchecked")
    List<MovieDTO> mapResultsToDto(Map<String, Object> response, String fallbackMediaType) {
        List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("results");

        return results.stream()
                .map(r -> {
                    String type = r.containsKey("media_type") ? (String) r.get("media_type") : fallbackMediaType;
                    String title = type.equals("tv") ? (String) r.get("name") : (String) r.get("title");
                    String dateField = type.equals("tv") ? "first_air_date" : "release_date";
                    String date = (String) r.get(dateField);
                    Integer year = (date != null && !date.isBlank()) ? Integer.parseInt(date.substring(0, 4)) : null;

                    return new MovieDTO(
                            String.valueOf(r.get("id")),
                            type,
                            title,
                            (String) r.get("poster_path"),
                            year
                    );
                })
                .toList();
    }
}
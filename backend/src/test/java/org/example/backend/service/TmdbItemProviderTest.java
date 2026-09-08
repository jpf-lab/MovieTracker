package org.example.backend.service;

import org.example.backend.dto.ItemDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.restclient.test.MockServerRestClientCustomizer;
import org.springframework.boot.restclient.test.autoconfigure.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureMockRestServiceServer
class TmdbItemProviderTest {

    @TestConfiguration()
    static class TestConfig {
        private final MockServerRestClientCustomizer customizer = new MockServerRestClientCustomizer();
        private final RestClient.Builder customizedBuilder = RestClient.builder();

        public TestConfig() {
            customizer.customize(customizedBuilder);
        }

        @Bean
        public RestClient.Builder restClientBuilder() {
            return customizedBuilder;
        }

        @Bean
        public MockRestServiceServer mockRestServiceServer() {
            return customizer.getServer(customizedBuilder);
        }
    }

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MockRestServiceServer mockRestServiceServer;

    private final TmdbItemProvider provider = new TmdbItemProvider(RestClient.builder());

    @BeforeEach
    void setUp() {
        mockRestServiceServer.reset();
    }

    @Test
    void mapResultsToDto_shouldMapMovieCorrectly() {
        // Given: eine TMDB-Antwort im rohen JSON-Format (als Map),
        // wie sie von /search/movie zurueckkommen wuerde
        Map<String, Object> response = Map.of(
                "results", List.of(
                        Map.of(
                                "id", 550,
                                "title", "Fight Club",
                                "poster_path", "/poster1.jpg",
                                "release_date", "1999-10-15"
                        )
                )
        );

        // When: die Mapping-Methode wird mit "movie" als Fallback
        // aufgerufen (weil TMDB bei /search/movie kein eigenes
        // media_type-Feld mitschickt)
        List<ItemDTO> result = provider.mapResultsToDto(response, "movie");

        // Then: alle Felder wurden korrekt uebernommen, das Jahr
        // wurde aus release_date extrahiert
        assertEquals(1, result.size());
        assertEquals("550", result.get(0).externalId());
        assertEquals("movie", result.get(0).mediaType());
        assertEquals("Fight Club", result.get(0).title());
        assertEquals("/poster1.jpg", result.get(0).posterPath());
        assertEquals(1999, result.get(0).year());
    }

    @Test
    void mapResultsToDto_shouldMapTvShowCorrectly() {
        // Given: eine TMDB-Antwort fuer eine Serie - hier heisst
        // das Titelfeld "name" statt "title", das Datumsfeld
        // "first_air_date" statt "release_date"
        Map<String, Object> response = Map.of(
                "results", List.of(
                        Map.of(
                                "id", 1396,
                                "name", "Breaking Bad",
                                "poster_path", "/poster2.jpg",
                                "first_air_date", "2008-01-20"
                        )
                )
        );

        // When
        List<ItemDTO> result = provider.mapResultsToDto(response, "tv");

        // Then
        assertEquals("Breaking Bad", result.get(0).title());
        assertEquals(2008, result.get(0).year());
        assertEquals("tv", result.get(0).mediaType());
    }

    @Test
    void mapResultsToDto_shouldReturnNullYear_whenDateIsMissing() {
        // Given: ein Ergebnis ohne Erscheinungsdatum (kommt bei
        // TMDB gelegentlich vor, z.B. bei noch nicht veroeffentlichten
        // Titeln)
        Map<String, Object> response = Map.of(
                "results", List.of(
                        Map.of(
                                "id", 999,
                                "title", "Unreleased Movie",
                                "poster_path", "/poster3.jpg",
                                "release_date", ""
                        )
                )
        );

        // When
        List<ItemDTO> result = provider.mapResultsToDto(response, "movie");

        // Then: kein Absturz, Jahr ist einfach null
        assertNull(result.get(0).year());
    }

    @Test
    void mapResultsToDto_shouldUseMediaTypeFromResponse_whenPresent() {
        // Given: eine Antwort wie von /trending/all/day, die pro
        // Ergebnis ein eigenes media_type-Feld mitliefert (statt
        // eines einheitlichen Fallback-Typs)
        Map<String, Object> response = Map.of(
                "results", List.of(
                        Map.of(
                                "id", 1,
                                "media_type", "tv",
                                "name", "Severance",
                                "poster_path", "/poster4.jpg",
                                "first_air_date", "2022-02-18"
                        )
                )
        );

        // When: fallbackMediaType wird absichtlich ignoriert, weil
        // die Antwort ihr eigenes media_type mitbringt
        List<ItemDTO> result = provider.mapResultsToDto(response, null);

        // Then
        assertEquals("tv", result.get(0).mediaType());
        assertEquals("Severance", result.get(0).title());
    }

    @Test
    void search_tv() throws Exception {
        //GIVEN
        String tmdbSearchTvResult = """
                    {
                       "page": 1,
                       "results": [
                         {
                           "adult": false,
                           "backdrop_path": "/m2QTmJhe36uKrkQjC1MsNV7Dcqp.jpg",
                           "genre_ids": [
                             16,
                             10765,
                             10759
                           ],
                           "id": 114478,
                           "origin_country": [
                             "JP",
                             "US"
                           ],
                           "original_language": "en",
                           "original_name": "Star Wars: Visions",
                           "overview": "Diese Sammlung animierter Kurzfilme aus aller Welt feiert den Mythos von Star Wars aus einzigartigen kulturellen Blickwinkeln.",
                           "popularity": 15.0735,
                           "poster_path": "/tyEgE0plST1EuwLaLi9pwAQrLyw.jpg",
                           "first_air_date": "2021-09-22",
                           "softcore": false,
                           "name": "Star Wars: Visionen",
                           "vote_average": 7.091,
                           "vote_count": 488
                         }
                      ],
                      "total_pages": 500,
                      "total_results": 10000
                    }
                """;

        String serviceSearchTvResponse = """
                    [
                        {
                          "externalId": "114478",
                          "mediaType": "tv",
                          "title": "Star Wars: Visionen",
                          "posterPath": "/tyEgE0plST1EuwLaLi9pwAQrLyw.jpg",
                          "year": 2021
                        }
                    ]
                """;
        //WHEN
        mockRestServiceServer.expect(
                        requestTo("https://api.themoviedb.org/3/search/tv?query=Star%20Wars&language=de-DE&year=2021")
                )
                .andRespond(withSuccess(tmdbSearchTvResult, MediaType.APPLICATION_JSON));
        //WHEN
        mockMvc.perform(
                        get("/api/filter?name=Star Wars&mediaType=tv&year=2021")
                )
                //THEN
                .andExpect(status().isOk())
                .andExpect(content().json(serviceSearchTvResponse));
    }

    @Test
    void search_movie() throws Exception {
        //GIVEN
        String tmdbSearchMovieResult = """
                    {
                      "page": 1,
                      "results": [
                            {
                              "adult": false,
                              "backdrop_path": "/zqkmTXzjkAgXmEWLRsY4UpTWCeo.jpg",
                              "genre_ids": [
                                12,
                                28,
                                878
                              ],
                              "id": 11,
                              "title": "Star Wars",
                              "original_language": "en",
                              "original_title": "Star Wars",
                              "overview": "Princess Leia is captured and held hostage by the evil Imperial forces in their effort to take over the galactic Empire. Venturesome Luke Skywalker and dashing captain Han Solo team together with the loveable robot duo R2-D2 and C-3PO to rescue the beautiful princess and restore peace and justice in the Empire.",
                              "popularity": 34.9029,
                              "poster_path": "/fai0rspsNeJCS69wHNjOdWxcI7P.jpg",
                              "release_date": "1977-05-25",
                              "softcore": false,
                              "video": false,
                              "vote_average": 8.208,
                              "vote_count": 22810
                            }
                      ],
                      "total_pages": 500,
                      "total_results": 10000
                    }
                """;

        String serviceSearchMovieResponse = """
                    [
                        {
                          "externalId": "11",
                          "mediaType": "movie",
                          "title": "Star Wars",
                          "posterPath": "/fai0rspsNeJCS69wHNjOdWxcI7P.jpg",
                          "year": 1977
                        }
                    ]
                """;
        //WHEN
        mockRestServiceServer.expect(
                        requestTo("https://api.themoviedb.org/3/search/movie?query=Star%20Wars&language=de-DE&year=1977")
                )
                .andRespond(withSuccess(tmdbSearchMovieResult, MediaType.APPLICATION_JSON));
        //WHEN
        mockMvc.perform(
                        get("/api/filter?name=Star Wars&mediaType=movie&year=1977")
                )
                //THEN
                .andExpect(status().isOk())
                .andExpect(content().json(serviceSearchMovieResponse));
    }

    @Test
    void getRandom() throws Exception {
        //GIVEN
        String tmdbTrendingResult = """
                {
                  "page": 1,
                  "results": [
                    {
                      "adult": false,
                      "backdrop_path": "/yG1wltFmkX5c5ocACKfpX0tp3SY.jpg",
                      "id": 236235,
                      "name": "The Gentlemen",
                      "original_name": "The Gentlemen",
                      "overview": "Als der adlige Eddie das Familienanwesen erbt, stelle er fest, dass ein riesiges Marihuana-Imperium dazugehört – und die Betreiber sich nicht in die Suppe spucken lassen.",
                      "poster_path": "/tw3tzfXaSpmUZIB8ZNqNEGzMBCy.jpg",
                      "media_type": "tv",
                      "original_language": "en",
                      "genre_ids": [
                        35,
                        18,
                        80
                      ],
                      "popularity": 233.9356,
                      "first_air_date": "2024-03-07",
                      "softcore": false,
                      "vote_average": 7.832,
                      "vote_count": 879,
                      "origin_country": [
                        "GB"
                      ]
                    }
                  ],
                  "total_pages": 500,
                  "total_results": 10000
                }
                """;


        String serviceTrendingResponse = """
                {
                  "externalId": "236235",
                  "mediaType": "tv",
                  "title": "The Gentlemen",
                  "posterPath": "/tw3tzfXaSpmUZIB8ZNqNEGzMBCy.jpg",
                  "year": 2024
                }
                """;
        mockRestServiceServer.expect(
                        requestTo("https://api.themoviedb.org/3/trending/all/day?language=de-DE")
                )
                .andRespond(withSuccess(tmdbTrendingResult, MediaType.APPLICATION_JSON));
        //WHEN
        mockMvc.perform(
                        get("/api/random")
                )
                //THEN
                .andExpect(status().isOk())
                .andExpect(content().json(serviceTrendingResponse));
    }
}

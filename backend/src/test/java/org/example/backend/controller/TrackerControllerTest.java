package org.example.backend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.restclient.test.autoconfigure.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureMockRestServiceServer
class TrackerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private MockRestServiceServer mockRestServiceServer;

    public String tmdbMultiResponse = """
            {
               "page": 1,
               "results": [
                 {
                   "id": 11,
                   "title": "Krieg der Sterne",
                   "original_title": "Star Wars",
                   "overview": "Seit der Vernichtung der Jedi-Ritter steht die Galaxie unter der grausamen Herrschaft des Imperiums. Nur eine kleine Gruppe von Rebellen, angeführt von der schönen Prinzessin Leia, widersetzt sich der dunklen Macht. Als es ihnen gelingt, die geheimen Baupläne für den gefährlichen Todesstern zu entwenden, gerät das Imperium in Aufruhr. Durch Zufall gelangen die Pläne ausgerechnet in die Hände des Farmerjungen Luke Skywalker, der spürt, dass er sein bisheriges Leben hinter sich lassen muss. Gemeinsam mit dem weisen Obi-Wan Kenobi, den Weltraumabenteurern Han Solo und Chewbacca sowie den Droiden R2-D2 und C-3PO nimmt er den Kampf gegen das mächtige Imperium auf. Es beginnt ein Wettlauf gegen die Zeit, denn der Kampfstern kann nur vor seiner Fertigstellung zerstört werden...",
                   "poster_path": "/crUUE14AROxNWsMdmyyqZNcUB6L.jpg",
                   "media_type": "movie",
                   "genre_ids": [
                     12,
                     28,
                     878
                   ],
                   "popularity": 35.5155,
                   "release_date": "1977-05-25",
                   "softcore": false,
                   "video": false,
                   "vote_average": 8.208,
                   "vote_count": 22782
                 },
                 {
                   "adult": false,
                   "backdrop_path": "/m2QTmJhe36uKrkQjC1MsNV7Dcqp.jpg",
                   "id": 114478,
                   "name": "Star Wars: Visionen",
                   "original_name": "Star Wars: Visions",
                   "overview": "Diese Sammlung animierter Kurzfilme aus aller Welt feiert den Mythos von Star Wars aus einzigartigen kulturellen Blickwinkeln.",
                   "poster_path": "/tyEgE0plST1EuwLaLi9pwAQrLyw.jpg",
                   "media_type": "tv",
                   "original_language": "en",
                   "genre_ids": [
                     16,
                     10765,
                     10759
                   ],
                   "popularity": 16.8257,
                   "first_air_date": "2021-09-22",
                   "softcore": false,
                   "vote_average": 7.091,
                   "vote_count": 487,
                   "origin_country": [
                     "JP",
                     "US"
                   ]
                 }
               ],
               "total_pages": 13,
               "total_results": 245
            }
            """;

    public String serviceMultiResponse = """
            {
                "page": 1,
                "results": [
                    {
                        "id": 11,
                        "media_type": "movie",
                        "poster_path": "https://image.tmdb.org/t/p/w342/crUUE14AROxNWsMdmyyqZNcUB6L.jpg",
                        "overview": "Seit der Vernichtung der Jedi-Ritter steht die Galaxie unter der grausamen Herrschaft des Imperiums. Nur eine kleine Gruppe von Rebellen, angeführt von der schönen Prinzessin Leia, widersetzt sich der dunklen Macht. Als es ihnen gelingt, die geheimen Baupläne für den gefährlichen Todesstern zu entwenden, gerät das Imperium in Aufruhr. Durch Zufall gelangen die Pläne ausgerechnet in die Hände des Farmerjungen Luke Skywalker, der spürt, dass er sein bisheriges Leben hinter sich lassen muss. Gemeinsam mit dem weisen Obi-Wan Kenobi, den Weltraumabenteurern Han Solo und Chewbacca sowie den Droiden R2-D2 und C-3PO nimmt er den Kampf gegen das mächtige Imperium auf. Es beginnt ein Wettlauf gegen die Zeit, denn der Kampfstern kann nur vor seiner Fertigstellung zerstört werden...",
                        "release_date": "1977-05-25",
                        "name": null,
                        "original_name": null,
                        "title": "Krieg der Sterne",
                        "original_title": "Star Wars",
                        "genre_ids": [
                            12,
                            28,
                            878
                        ]
                    },
                    {
                        "id": 114478,
                        "media_type": "tv",
                        "poster_path": "https://image.tmdb.org/t/p/w342/tyEgE0plST1EuwLaLi9pwAQrLyw.jpg",
                        "overview": "Diese Sammlung animierter Kurzfilme aus aller Welt feiert den Mythos von Star Wars aus einzigartigen kulturellen Blickwinkeln.",
                        "release_date": null,
                        "name": "Star Wars: Visionen",
                        "original_name": "Star Wars: Visions",
                        "title": null,
                        "original_title": null,
                        "genre_ids": [
                            16,
                            10765,
                            10759
                        ]
                    }
                ],
                "total_pages": 13,
                "total_results": 245
            }
            """;

    public String tmdbConfigurationResponse = """
                    {
                      "images": {
                        "base_url": "http://image.tmdb.org/t/p/",
                        "secure_base_url": "https://image.tmdb.org/t/p/",
                        "backdrop_sizes": [
                          "w300",
                          "w780",
                          "w1280",
                          "original"
                        ],
                        "logo_sizes": [
                          "w45",
                          "w92",
                          "w154",
                          "w185",
                          "w300",
                          "w500",
                          "original"
                        ],
                        "poster_sizes": [
                          "w92",
                          "w154",
                          "w185",
                          "w342",
                          "w500",
                          "w780",
                          "original"
                        ],
                        "profile_sizes": [
                          "w45",
                          "w185",
                          "h632",
                          "original"
                        ],
                        "still_sizes": [
                          "w92",
                          "w185",
                          "w300",
                          "original"
                        ]
                      },
                      "change_keys": [
                        "adult",
                        "air_date",
                        "also_known_as",
                        "alternative_titles",
                        "biography",
                        "birthday",
                        "budget",
                        "cast",
                        "certifications",
                        "character_names",
                        "created_by",
                        "crew",
                        "deathday",
                        "episode",
                        "episode_number",
                        "episode_run_time",
                        "freebase_id",
                        "freebase_mid",
                        "general",
                        "genres",
                        "guest_stars",
                        "homepage",
                        "images",
                        "imdb_id",
                        "languages",
                        "name",
                        "network",
                        "origin_country",
                        "original_name",
                        "original_title",
                        "overview",
                        "parts",
                        "place_of_birth",
                        "plot_keywords",
                        "production_code",
                        "production_companies",
                        "production_countries",
                        "releases",
                        "revenue",
                        "runtime",
                        "season",
                        "season_number",
                        "season_regular",
                        "spoken_languages",
                        "status",
                        "tagline",
                        "title",
                        "translations",
                        "tvdb_id",
                        "tvrage_id",
                        "type",
                        "video",
                        "videos"
                      ]
                    }
            """;

    @Test
    void findByQuery() throws Exception {
        //GIVEN
//        String query = "Star Wars";
        mockRestServiceServer.expect(
                        requestTo("https://api.themoviedb.org/3/search/multi?query=Star%20Wars&include_adult=false&language=de-DE&page=1")
                )
                .andRespond(withSuccess(tmdbMultiResponse, MediaType.APPLICATION_JSON));
        mockRestServiceServer.expect(
                        requestTo("https://api.themoviedb.org/3/configuration")
                )
                .andRespond(withSuccess(tmdbConfigurationResponse, MediaType.APPLICATION_JSON));
        //WHEN
        mockMvc.perform(
                        get("/api/search?query=Star Wars")
                )
                //THEN
                .andExpect(status().isOk())
                .andExpect(content().json(serviceMultiResponse));
    }
}
package com.example.game.service_api.commons.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GamePostRequest {
    private String name;
    private String genre;
    private String platforms;
    private Integer releaseYear;
    private String company;
    private Double rating;
    private Double price;

    @JsonIgnore
    private Long creatorUserId;
    @JsonIgnore
    private String creatorUsername;
}
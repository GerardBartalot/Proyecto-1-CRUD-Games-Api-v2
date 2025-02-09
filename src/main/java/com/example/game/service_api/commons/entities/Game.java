package com.example.game.service_api.commons.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "games_list")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long gameId;

    @Column(nullable = false, unique = true, name = "name")
    private String gameName;

    @Column(nullable = false, name = "created by")
    private String createdByUser;

    @Column(name = "genre")
    private String genre;

    @Column(name = "platforms")
    private String platforms;

    @Column(name = "release year")
    private Integer releaseYear;

    @Column(name = "company")
    private String company;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "price")
    private Double price;

    @Column(name = "updated by")
    private String updatedByUser;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created at", updatable = false, nullable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated at")
    private Date updatedAt;

}
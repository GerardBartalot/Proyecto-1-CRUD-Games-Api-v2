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
    @Column(nullable = false, unique = true, name = "id")
    private Long id;

    @Column(nullable = false, unique = true, name = "name")
    private String name;

    @Column(name = "creator id")
    private Long creatorUserId;

    @Column(name = "creator username")
    private String creatorUsername;

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

    @Column(name = "updator id")
    private Long updatorUserId;

    @Column(name = "updator username")
    private String updatorUsername;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created at", updatable = false, nullable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated at")
    private Date updatedAt;

}
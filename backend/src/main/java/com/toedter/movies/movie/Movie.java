package com.toedter.movies.movie;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.toedter.movies.director.Director;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Entity
@NoArgsConstructor

public class Movie {
    @Id @GeneratedValue @JsonIgnore
    private Long id;

    private String imdbId;
    private String title;
    private long year;
    private double rating;
    private int rank;
    private String thumb;

    @JsonIgnore
    @ManyToMany(mappedBy = "movies", fetch = FetchType.EAGER)
    List<Director> directors = new ArrayList<>();

    @Transient
    Map<String, Object> _embedded = new HashMap<>();

    public Movie(String imdbId, String title, long year, double rating, int rank, String thumb) {
        this.imdbId = imdbId;
        this.title = title;
        this.year = year;
        this.rating = rating;
        this.rank = rank;
        this.thumb = thumb;
    }

    public void addDirector(Director director) {
        directors.add(director);
    }
}

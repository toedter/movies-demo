package com.toedter.movies.director;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.toedter.movies.movie.Movie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Director {
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
    private String name;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    List<Movie> movies = new ArrayList<>();

    @Transient
    Map<String, Object> _embedded;

    public Director(String name) {
        this.name = name;
    }

    public void addMovie(Movie movie) {
        this.movies.add(movie);
    }

}

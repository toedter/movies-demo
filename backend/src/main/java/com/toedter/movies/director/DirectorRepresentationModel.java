package com.toedter.movies.director;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.toedter.movies.movie.EmbeddedMovieRepresentationModel;
import com.toedter.movies.movie.Movie;
import lombok.Data;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import java.util.Arrays;
import java.util.stream.Collectors;

@Data
public class DirectorRepresentationModel extends EntityModel<Director> {
    @JsonUnwrapped
    CollectionModel<EntityModel<Movie>> movies;

    public DirectorRepresentationModel(Director director) {
        super(director);
        initializeMovies(director);
    }

    public DirectorRepresentationModel(Director director, Link... links) {
        super(director, Arrays.asList(links));
        initializeMovies(director);
    }

    private void initializeMovies(Director director) {
        movies = new CollectionModel<>(
                director.getMovies()
                        .stream()
                        .map(movie -> new EmbeddedMovieRepresentationModel(movie))
                        .collect(Collectors.toList()));
    }

}

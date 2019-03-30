package com.toedter.movies.director;

import com.toedter.movies.movie.Movie;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;


public class EmbeddedMovieRepresentationModel extends EntityModel<Movie> {

    public EmbeddedMovieRepresentationModel(Movie movie) {
        super(movie);
    }

    public EmbeddedMovieRepresentationModel(Movie movie, Link... links) {

    }

}

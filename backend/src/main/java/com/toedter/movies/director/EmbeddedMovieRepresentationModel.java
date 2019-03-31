package com.toedter.movies.director;

import com.toedter.movies.movie.EmbeddedMovie;
import com.toedter.movies.movie.Movie;
import com.toedter.movies.movie.MovieController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


public class EmbeddedMovieRepresentationModel extends EntityModel<EmbeddedMovie> {
    public EmbeddedMovieRepresentationModel(Movie movie) {
        super(new EmbeddedMovie(movie), linkTo(methodOn(MovieController.class).findOne(movie.getId())).withSelfRel());
    }

    public EmbeddedMovieRepresentationModel(Movie movie, Link... links) {
        super(new EmbeddedMovie(movie), links);
    }

}

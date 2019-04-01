package com.toedter.movies.movie;

import com.toedter.movies.movie.Movie;
import com.toedter.movies.movie.MovieController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


public class EmbeddedMovieRepresentationModel extends EntityModel<Movie> {

    public EmbeddedMovieRepresentationModel(Movie movie) {
        super(movie, linkTo(methodOn(MovieController.class).findOne(movie.getId())).withSelfRel());
    }

    public EmbeddedMovieRepresentationModel(Movie movie, Link... links) {
    }

}

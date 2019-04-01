package com.toedter.movies.movie;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.toedter.movies.director.EmbeddedDirectorRepresentationModel;
import lombok.Data;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Data
public class MovieRepresentationModel extends EntityModel<Movie> {
    @JsonUnwrapped
    CollectionModel<EmbeddedDirectorRepresentationModel> directors;

    public MovieRepresentationModel(Movie movie) {
        super(movie, linkTo(methodOn(MovieController.class).findOne(movie.getId())).withSelfRel());
        initializeDirectors(movie);
    }

    public MovieRepresentationModel(Movie movie, Link... links) {
        super(movie, Arrays.asList(links));
        initializeDirectors(movie);
    }

    private void initializeDirectors(Movie movie) {
        directors = new CollectionModel<>(
                movie.getDirectors()
                        .stream()
                        .map(director -> new EmbeddedDirectorRepresentationModel(director))
                        .collect(Collectors.toList()));
    }

}

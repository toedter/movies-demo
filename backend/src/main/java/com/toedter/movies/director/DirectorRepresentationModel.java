package com.toedter.movies.director;

import com.toedter.movies.movie.Movie;
import com.toedter.movies.movie.MovieController;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Data
public class DirectorRepresentationModel extends RepresentationModel<DirectorRepresentationModel> {
    private String name;

    public DirectorRepresentationModel(Director director) {
        this.name = director.getName();

        add(linkTo(methodOn(DirectorController.class).findOne(director.getId())).withSelfRel());

        for (Movie movie : director.getMovies()) {
            add(linkTo(methodOn(MovieController.class)
                    .findOne(movie.getId()))
                    .withRel("movies")
                    .withTitle(movie.getTitle()));
        }
    }
}

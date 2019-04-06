package com.toedter.movies.director;

import com.toedter.movies.movie.Movie;
import com.toedter.movies.movie.MovieController;
import lombok.Data;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Data
public class DirectorRepresentationModel extends EntityModel<Director> {
    private String name;

    public DirectorRepresentationModel(Director director) {
        super(director);
        initialize(director);
    }

    public DirectorRepresentationModel(Director director, Link... links) {
        super(director, links);
        initialize(director);
    }

    private void initialize(Director director) {
        this.name = director.getName();
        for (Movie movie : director.getMovies()) {
            add(linkTo(methodOn(MovieController.class)
                    .findOne(movie.getId()))
                    .withRel("movies")
                    .withTitle(movie.getTitle()));
        }

    }

}

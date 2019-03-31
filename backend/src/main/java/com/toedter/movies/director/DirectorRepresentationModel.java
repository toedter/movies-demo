package com.toedter.movies.director;

import com.toedter.movies.movie.EmbeddedMovie;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


public class DirectorRepresentationModel extends EntityModel<Director> {
//    @JsonUnwrapped
//    CollectionModel<EntityModel<Movie>> movies;
//
//    public CollectionModel<EntityModel<Movie>> getMovies() {
//        return movies;
//    }

    public DirectorRepresentationModel(Director director) {
        super(director);
        initializeMovies(director);
    }

    public DirectorRepresentationModel(Director director, Link... links) {
        super(director, links);
        initializeMovies(director);
    }

    private void initializeMovies(Director director) {
        final List<EntityModel<EmbeddedMovie>> movies = director.getMovies()
                .stream()
                .map(EmbeddedMovieRepresentationModel::new)
                .collect(Collectors.toList());
        if(director._embedded == null) {
            director._embedded = new HashMap<>();
        }
        director._embedded.put("directed-movies", movies);

//        for(EntityModel<Movie> movieModel: movies) {
//            addEmbeddedResource("movies", movieModel);
//        }

//        Movie movie1 = new Movie();
//        movie1.setId(1L);
//        movie1.setTitle("ET");
//
//        Movie movie2 = new Movie();
//        movie2.setId(2L);
//        movie2.setTitle("Jaws");
//
//        EntityModel<Movie> p1 = new EntityModel<>(movie1);
//        EntityModel<Movie> p2 = new EntityModel<>(movie2);
//
//        this.movies = new CollectionModel<>(Arrays.asList(p1, p2));
    }

}

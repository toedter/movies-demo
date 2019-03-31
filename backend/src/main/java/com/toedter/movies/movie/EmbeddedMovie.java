package com.toedter.movies.movie;

import lombok.Data;

@Data
public class EmbeddedMovie {
    private String title;

    public EmbeddedMovie() {
    }

    public EmbeddedMovie(Movie movie) {
        this.title = movie.getTitle();
    }
}

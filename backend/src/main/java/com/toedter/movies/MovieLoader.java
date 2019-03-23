package com.toedter.movies;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

@Component
@Slf4j
class MovieLoader {

    @Bean
    CommandLineRunner init(MovieRepository repository) {

        return args -> {
            String moviesJson;
            ObjectMapper mapper = new ObjectMapper();

            File file = ResourceUtils.getFile("classpath:static/movie-data/movies-250.json");

            moviesJson = readFile(file.getPath(), StandardCharsets.UTF_8);
            JsonNode rootNode = mapper.readValue(moviesJson, JsonNode.class);

            JsonNode movies = rootNode.get("movies");
            int rating = 1;
            Iterator<JsonNode> iterator = movies.iterator();
            while (iterator.hasNext()) {
                JsonNode movie = iterator.next();
                handleMovie(repository, rating++, movie.get("imdbID").asText(), movie);
            }
        };
    }

    private void handleMovie(MovieRepository movieRepository, int rank, String id, JsonNode rootNode) {
        String title = rootNode.get("Title").asText();
        String director = rootNode.get("Director").asText();
        long year = rootNode.get("Year").asLong();
        double imdbRating = rootNode.get("imdbRating").asDouble();

        String movieImage = "/movie-data/thumbs/" + id + ".jpg";
        Movie movie = new Movie(id, title, director, year, imdbRating, rank, movieImage);
        if (movieRepository != null) {
            movieRepository.save(movie);
        }
        log.info("found movie: " + rank + ": " + title + " (" + year + ") " + imdbRating);
    }

    private String readFile(String path, Charset encoding)
            throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

}

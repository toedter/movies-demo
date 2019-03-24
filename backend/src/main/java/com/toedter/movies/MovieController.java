/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.toedter.movies;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
class MovieController {

    private final MovieRepository repository;

    MovieController(MovieRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/movies")
    ResponseEntity<CollectionModel<EntityModel<Movie>>> findAll(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size) {

        final int finalSize = size;
        final int finalPage = page;
        final PageRequest pageRequest = PageRequest.of(finalPage, finalSize);

        final Page<Movie> pagedResult = repository.findAll(pageRequest);

        List<EntityModel<Movie>> movieResources = StreamSupport.stream(pagedResult.spliterator(), false)
                .map(movie -> new EntityModel<>(movie,
                        linkTo(methodOn(MovieController.class).findOne(movie.getId())).withSelfRel()
                                .andAffordance(afford(methodOn(MovieController.class).updateMovie(null, movie.getId())))
                                .andAffordance(afford(methodOn(MovieController.class).deleteMovie(movie.getId()))),
                        linkTo(methodOn(MovieController.class).findAll(finalPage, finalSize)).withRel("movies")))
                .collect(Collectors.toList());

        Link selfLink = linkTo(MovieController.class).slash("movies").withSelfRel();
        Link templatedLink = new Link(selfLink.getHref() + "{?size,page}").withSelfRel();

        final CollectionModel<EntityModel<Movie>> entityModels =
                new CollectionModel<>(movieResources, templatedLink.andAffordance(afford(methodOn(MovieController.class).newMovie(null))));

        final Pageable prev = pageRequest.previous();
        if (prev.getPageNumber() < page) {
            Link prevLink = new Link(selfLink.getHref() + "?page=" + prev.getPageNumber() + "&size=" + prev.getPageSize()).withRel(IanaLinkRelations.PREV);
            entityModels.add(prevLink);
        }

        final Pageable next = pageRequest.next();
        if (next.getPageNumber() > page && next.getPageNumber() < pagedResult.getTotalPages()) {
            Link nextLink = new Link(selfLink.getHref() + "?page=" + next.getPageNumber() + "&size=" + next.getPageSize()).withRel(IanaLinkRelations.NEXT);
            entityModels.add(nextLink);
        }

        if (page > 0) {
            Link firstLink = new Link(selfLink.getHref() + "?page=0&size=" + size).withRel(IanaLinkRelations.FIRST);
            entityModels.add(firstLink);
        }

        if (page < pagedResult.getTotalPages() - 1) {
            Link lastLink = new Link(selfLink.getHref() + "?page=" + (pagedResult.getTotalPages() - 1) + "&size=" + size).withRel(IanaLinkRelations.LAST);
            entityModels.add(lastLink);
        }

        return ResponseEntity.ok(entityModels);
    }

    @PostMapping("/movies")
    ResponseEntity<?> newMovie(@RequestBody Movie movie) {

        Movie savedMovie = repository.save(movie);

        return new EntityModel<>(savedMovie,
                linkTo(methodOn(MovieController.class).findOne(savedMovie.getId())).withSelfRel()
                        .andAffordance(afford(methodOn(MovieController.class).updateMovie(null, savedMovie.getId())))
                        .andAffordance(afford(methodOn(MovieController.class).deleteMovie(savedMovie.getId()))),
                linkTo(methodOn(MovieController.class).findAll(0, 10)).withRel("movies")).getLink(IanaLinkRelations.SELF)
                .map(Link::getHref) //
                .map(href -> {
                    try {
                        return new URI(href);
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                }) //
                .map(uri -> ResponseEntity.noContent().location(uri).build())
                .orElse(ResponseEntity.badRequest().body("Unable to create " + movie));
    }

    @GetMapping("/movies/{id}")
    ResponseEntity<EntityModel<Movie>> findOne(@PathVariable String id) {

        return repository.findById(id)
                .map(movie -> new EntityModel<>(movie,
                        linkTo(methodOn(MovieController.class).findOne(movie.getId())).withSelfRel()
                                .andAffordance(afford(methodOn(MovieController.class).updateMovie(null, movie.getId())))
                                .andAffordance(afford(methodOn(MovieController.class).deleteMovie(movie.getId()))),
                        linkTo(methodOn(MovieController.class).findAll(0, 10)).withRel("movies")))
                .map(ResponseEntity::ok) //
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/movies/{id}")
    ResponseEntity<?> updateMovie(@RequestBody Movie movie, @PathVariable String id) {

        Movie movieToUpdate = movie;
        movieToUpdate.setId(id);

        Movie updatedMovie = repository.save(movieToUpdate);

        return new EntityModel<>(updatedMovie,
                linkTo(methodOn(MovieController.class).findOne(updatedMovie.getId())).withSelfRel()
                        .andAffordance(afford(methodOn(MovieController.class).updateMovie(null, updatedMovie.getId())))
                        .andAffordance(afford(methodOn(MovieController.class).deleteMovie(updatedMovie.getId()))),
                linkTo(methodOn(MovieController.class).findAll(0, 10)).withRel("movies")).getLink(IanaLinkRelations.SELF)
                .map(Link::getHref).map(href -> {
                    try {
                        return new URI(href);
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                }) //
                .map(uri -> ResponseEntity.noContent().location(uri).build()) //
                .orElse(ResponseEntity.badRequest().body("Unable to update " + movieToUpdate));
    }

    @DeleteMapping("/movies/{id}")
    ResponseEntity<?> deleteMovie(@PathVariable String id) {

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}

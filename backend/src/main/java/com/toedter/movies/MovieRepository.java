package com.toedter.movies;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface MovieRepository extends PagingAndSortingRepository<Movie, String> {
}

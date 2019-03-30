package com.toedter.movies.director;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface DirectorRepository extends PagingAndSortingRepository<Director, Long> {
    Director findByName(String name);
}

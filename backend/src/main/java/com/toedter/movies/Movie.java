package com.toedter.movies;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    @Id
    private String id;
    private String title;
    private long year;
    private double rating;
    private int rank;
    private String thumb;
}

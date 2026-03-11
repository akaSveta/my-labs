package com.goncharova.labs.dto;

public class Movie {
    private Integer id;
    private String title;
    private Double rating;

    public Movie() {}

    public Movie(String title, Double rating) {
        this.title = title;
        this.rating = rating;
    }

    public Movie(Integer id, String title, Double rating) {
        this.id = id;
        this.title = title;
        this.rating = rating;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }
}
package com.goncharova.labs.dto;

public class CreateMovieRequest {
    private String title;
    private Double rating;

    public CreateMovieRequest() {
    }

    public CreateMovieRequest(String title, Double rating) {
        this.title = title;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public Double getRating() {
        return rating;
    }

}
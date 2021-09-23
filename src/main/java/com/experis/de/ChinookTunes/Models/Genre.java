package com.experis.de.ChinookTunes.Models;

public class Genre {
    private Integer genreId;
    private String name;

    public Integer getGenreId() {
        return genreId;
    }

    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Genre(Integer genreId, String name) {
        this.genreId = genreId;
        this.name = name;
    }
}

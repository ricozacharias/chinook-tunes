package com.experis.de.ChinookTunes.Models;

public class CustomerGenre {

    private Integer genreId;
    private String genreName;
    private Integer genreCount;

    public CustomerGenre(Integer genreId, String genreName, Integer genreCount) {
        this.genreId = genreId;
        this.genreName = genreName;
        this.genreCount = genreCount;
    }

    public Integer getGenreId() {
        return genreId;
    }

    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public Integer getGenreCount() {
        return genreCount;
    }

    public void setGenreCount(Integer genreCount) {
        this.genreCount = genreCount;
    }
}

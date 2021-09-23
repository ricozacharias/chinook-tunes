package com.experis.de.ChinookTunes.Models;

public class Artist {
    private Integer artistId;
    private String name;

    public Artist(Integer artistId, String name)
    {
        this.artistId = artistId;
        this.name = name;
    }

    public Integer getArtistId() {
        return artistId;
    }

    public void setArtistId(Integer artistId) {
        this.artistId = artistId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

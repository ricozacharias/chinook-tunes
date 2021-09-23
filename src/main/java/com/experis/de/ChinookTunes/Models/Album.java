package com.experis.de.ChinookTunes.Models;

public class Album {
    private Integer albumId;
    private String title;

    public Album(Integer albumId, String title) {
        this.albumId = albumId;
        this.title = title;
    }

    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

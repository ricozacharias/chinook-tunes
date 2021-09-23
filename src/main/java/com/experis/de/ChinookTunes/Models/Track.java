package com.experis.de.ChinookTunes.Models;

public class Track {
    private Integer trackId;
    private String trackName;
    private String albumTitle;
    private String artistName;
    private String genreName;

    public Track(Integer trackId, String trackName) {
        this(trackId, trackName, null, null, null);
    }

    public Track(Integer trackId, String trackName, String albumName, String artistName, String genreName) {
        this.trackId = trackId;
        this.trackName = trackName;
        this.albumTitle = albumName;
        this.artistName = artistName;
        this.genreName = genreName;
    }


    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public Integer getTrackId() {
        return trackId;
    }

    public void setTrackId(Integer trackId) {
        this.trackId = trackId;
    }
}

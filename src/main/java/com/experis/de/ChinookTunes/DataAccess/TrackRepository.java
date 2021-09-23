package com.experis.de.ChinookTunes.DataAccess;

import com.experis.de.ChinookTunes.Models.Track;

import java.util.List;

public interface TrackRepository {
    List<Track> getAllTracks(Integer limit, Integer offset, String orderBy);
    Track getTrackById(Integer trackId);
    List<Track> searchTracks(String searchText);
}

package com.experis.de.ChinookTunes.DataAccess;

import com.experis.de.ChinookTunes.Models.Artist;

import java.util.ArrayList;
import java.util.List;

public interface ArtistRepository {
    List<Artist> getAllArtists(Integer limit, Integer offset, String orderBy);

    Artist getArtistById(Integer ArtistId);
}

package com.experis.de.ChinookTunes.DataAccess;

import com.experis.de.ChinookTunes.Models.Genre;

import java.util.List;

public interface GenreRepository {
    List<Genre> getAllGenres(Integer limit, Integer offset, String orderBy);
    Genre getGenreById(Integer GenreId);
}

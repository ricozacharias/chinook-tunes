package com.experis.de.ChinookTunes.Controllers;

import com.experis.de.ChinookTunes.DataAccess.ArtistRepository;
import com.experis.de.ChinookTunes.DataAccess.GenreRepository;
import com.experis.de.ChinookTunes.DataAccess.TrackRepository;
import com.experis.de.ChinookTunes.Models.Artist;
import com.experis.de.ChinookTunes.Models.Genre;
import com.experis.de.ChinookTunes.Models.Track;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    // Configure some endpoints to manage crud
    private final TrackRepository trackRepository;
    private final GenreRepository genreRepository;
    private final ArtistRepository artistRepository;

    public HomeController(TrackRepository trackRepository, GenreRepository genreRepository, ArtistRepository artistRepository) {
        this.trackRepository = trackRepository;
        this.genreRepository = genreRepository;
        this.artistRepository = artistRepository;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
        List<Track> tracks = trackRepository.getAllTracks(5, 0, "RANDOM()");
        List<Genre> genres = genreRepository.getAllGenres(5, 0, "RANDOM()");
        List<Artist> artists = artistRepository.getAllArtists(5, 0, "RANDOM()");

        model.addAttribute("tracks", tracks);
        model.addAttribute("genres", genres);
        model.addAttribute("artists", artists);

        return "home";
    }
}

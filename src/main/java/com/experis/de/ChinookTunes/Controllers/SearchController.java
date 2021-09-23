package com.experis.de.ChinookTunes.Controllers;

import com.experis.de.ChinookTunes.DataAccess.TrackRepository;
import com.experis.de.ChinookTunes.Models.Track;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {

    private TrackRepository trackRepository;

    public SearchController(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(Model model) {

        // initialize with empty data
        model.addAttribute("searchtext", "");
        model.addAttribute("tracks", new ArrayList<Track>());

        return "search";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String search(Model model, @RequestParam(value="searchtext") String searchText) {

        List<Track> tracks = trackRepository.searchTracks(searchText);

        model.addAttribute("searchtext", searchText);
        model.addAttribute("tracks", tracks);

        return "search";
    }
}

package com.store.rest;

import com.store.dao.TrackDAO;
import com.store.model.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrackController {
    @Autowired
    private TrackDAO trackDAO;

    @GetMapping(path="/track/all")
    public @ResponseBody
    Iterable<Track> getAllTracks() {
        // This returns a JSON array with all the tracks
        return trackDAO.getAllTracks();
    }
}

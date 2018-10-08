package com.store.dao;

import com.store.model.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class TrackDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public TrackDAO(JdbcTemplate jdbcTemp) {
        this.jdbcTemplate = jdbcTemp;
    }

    public Collection<Track> getTracksByAlbumId(int albumId){
        Collection<Track> tracks = new ArrayList<Track>();

        this.jdbcTemplate.query(
                "SELECT id, title FROM tracks WHERE album = ?", new Object[] { albumId },
                (rs, rowNum) -> new Track(rs.getInt("id"), rs.getString("title"),albumId)
        ).forEach(track -> tracks.add(track) );

        return tracks;
    }

    public Collection<Track> getAllTracks(){
        Collection<Track> tracks = new ArrayList<Track>();
        this.jdbcTemplate.query(
                "SELECT * FROM tracks", new Object[] { },
                (rs, rowNum) ->
                        new Track(rs.getInt("id"), rs.getString("title"), rs.getInt("album"))
        ).forEach(album -> tracks.add(album));

        return tracks;
    }

}
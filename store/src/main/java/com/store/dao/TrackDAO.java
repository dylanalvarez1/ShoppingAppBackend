/**package com.store.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import com.store.model.*;
import java.util.Collection;
import java.util.ArrayList;


public class TrackDAO {
    private JdbcTemplate jdbcTemplate;

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

}
**/
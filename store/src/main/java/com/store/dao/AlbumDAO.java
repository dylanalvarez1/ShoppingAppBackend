package com.store.dao;

import com.store.model.Album;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class AlbumDAO {

    private JdbcTemplate jdbcTemplate;


    @Autowired
    public AlbumDAO(JdbcTemplate jdbcTemp) {
        this.jdbcTemplate = jdbcTemp;
    }

    public Collection<Album> getAllAlbums(){
        Collection<Album> albums = new ArrayList<Album>();
        this.jdbcTemplate.query(
                "SELECT * FROM albums", new Object[] { },
                (rs, rowNum) -> new Album(rs.getInt("id"), rs.getString("title"))
        ).forEach(album -> albums.add(album));

        return albums;
    }


}


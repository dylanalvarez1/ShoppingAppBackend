package com.store.rest;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.ArrayList;


import com.store.dao.*;
import com.store.model.*;

@Service
public class AlbumService {
	
	//@Autowired
	private AlbumDAO albumDAO = new AlbumDAO();
	
 
	public String getMsg( String msg) { 
		return "Hello : " + msg;
	}

	public String getAllAlbums() {
		String retString = "";
		Collection<Album> albums = albumDAO.getAllAlbums();
		for (Album album : albums) {
			retString += album.toString();
		}

		return retString;
	}


}
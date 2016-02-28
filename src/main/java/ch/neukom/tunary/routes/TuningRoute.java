package ch.neukom.tunary.routes;

import ch.neukom.tunary.beans.Album;
import ch.neukom.tunary.beans.Band;
import ch.neukom.tunary.beans.Song;
import ch.neukom.tunary.beans.Tuneable;
import ch.neukom.tunary.beans.Tuning;
import ch.neukom.tunary.services.database.DatabaseHandler;
import ch.neukom.tunary.services.database.Persistable;
import ch.neukom.tunary.services.gson.GsonUtil;
import spark.Request;

import static spark.Spark.*;

import org.neo4j.ogm.cypher.Filters;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

public class TuningRoute {
	private DatabaseHandler db;
	private Gson gson;
	
	public TuningRoute(DatabaseHandler db) {
		this.db = db;
		staticFileLocation("/pages");
		gson = GsonUtil.create();
		
		get("/bands", (request, response) -> gson.toJson(getBands()));
		get("/band/:id", (request, response) -> {
			Long id = Long.valueOf(request.params(":id"));
			Band band = findBand(id);
			return gson.toJson(band);
		});
		post("/band", (request, response) -> createOrUpdate(request, Band.class));
		
		get("/albums", (request, response) -> gson.toJson(getAlbums()));
		get("/albums/:id", (request, response) -> {
			Long id = Long.valueOf(request.params(":id"));
			return gson.toJson(getAlbumsForBand(id));
		});
		get("/album/:id", (request, response) -> {
			Long id = Long.valueOf(request.params(":id"));
			return gson.toJson(findAlbum(id));
		});
		post("/album", (request, response) -> createOrUpdate(request, Album.class));
		
		get("/songs", (request, response) -> gson.toJson(getSongs()));
		get("/songs/:id", (request, response) -> {
			Long id = Long.valueOf(request.params(":id"));
			return gson.toJson(getSongsForBand(id));
		});
		get("/song/:id", (request, response) -> {
			Long id = Long.valueOf(request.params(":id"));
			return gson.toJson(findSong(id));
		});
		post("/song", (request, response) -> createOrUpdate(request, Song.class));
		
		get("/tuneables", (request, response) -> gson.toJson(getTuneables()));
		get("/findTuneable/:name", (request, response) -> {
			String name = request.params(":name");
			Iterable<Tuneable> tuneables = searchTuneables(name);
			return gson.toJson(tuneables);
		});
		
		get("/tunings", (request, response) -> gson.toJson(getTunings()));
		get("/tuning/:className/:id", (request, response) -> {
			Long id = Long.valueOf(request.params(":id"));
			String className = request.params(":className");
			return gson.toJson(findTunings(className, id));
		});
	}

	private Iterable<Band> getBands() {
		return db.find(Band.class);
	}

	private Iterable<Album> getAlbums() {
		return db.find(Album.class, 2);
	}

	private Iterable<Album> getAlbumsForBand(Long id) {
		return db.findByQuery(Album.class, "MATCH path=(Album) --> (band:Band) WHERE id(band) = {id} RETURN path", ImmutableMap.of("id", id));
	}

	private Iterable<Song> getSongs() {
		return db.find(Song.class, 3);
	}

	private Iterable<Album> getSongsForBand(Long id) {
		return db.findByQuery(Album.class, "MATCH path=(Song) --> (album:Album) --> (Band) WHERE id(album) = {id} RETURN path", ImmutableMap.of("id", id));
	}

	private Iterable<Tuneable> getTuneables() {
		return db.find(Tuneable.class);
	}
	
	private Band findBand(Long id) {
		return db.find(Band.class, id);
	}
	
	private Album findAlbum(Long id) {
		return db.find(Album.class, id, 2);
	}
	
	private Song findSong(Long id) {
		return db.find(Song.class, id, 3);
	}
	
	private Iterable<Tuning> findTunings(String className, Long id) {
		return db.findByQuery(Tuning.class, "MATCH (tuneable:{className}) RETURN tuneable", ImmutableMap.of("id", id, "class", className));
	}

	private Iterable<Tuning> getTunings() {
		return db.find(Tuning.class);
	}
	
	private Iterable<Tuneable> searchTuneables(String name) {
		return db.findByFilter(Tuneable.class, new Filters().add("name", name));
	}
	
	private <T extends Persistable> T createOrUpdate(Request request, Class<T> type) {
		T persistable = gson.fromJson(request.body(), type);
		return db.createOrUpdate(persistable);
	}
}

package max.nlp.dal.lyrics;

import java.net.UnknownHostException;
import java.util.List;

import max.nlp.dal.lyrics.types.Song;

import com.github.jmkgreen.morphia.Datastore;
import com.github.jmkgreen.morphia.Morphia;
import com.github.jmkgreen.morphia.query.Query;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class LyricaDB {

	public final static String DB_NAME = "lyrica";
	private static LyricaDB instance = null;
	private Datastore ds = null;

	public static LyricaDB getDefault() {
		if (instance == null) {
			instance = new LyricaDB();
		}
		return instance;
	}

	public void save(Song s) {
		ds.save(s);
	}

	@SuppressWarnings("deprecation")
	public LyricaDB() {
		Mongo mongo;
		try {
			mongo = new Mongo();
			ds = new Morphia().createDatastore(mongo, DB_NAME);
			ds.ensureIndexes();
			ds.ensureCaps();

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<Song> getAllRecords() {
		try {
			Query<Song> q = ds.createQuery(Song.class);
			return q.asList();
		} catch (MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public List<Song> getSongByArtist(String artist) {
		try {
			Query<Song> q = ds.createQuery(Song.class).field("author")
					.equal(artist);
			return q.asList();
		} catch (MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public List<Song> getSongByLanguage(String language) {
		try {
			Query<Song> q = ds.createQuery(Song.class).field("language")
					.equal(language);
			return q.asList();
		} catch (MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public List<Song> getSongsByTitle(String title) {
		try {
			Query<Song> q = ds.createQuery(Song.class).field("title")
					.equal(title);
			return q.asList();
		} catch (MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}

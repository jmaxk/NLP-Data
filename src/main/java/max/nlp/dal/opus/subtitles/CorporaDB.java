package max.nlp.dal.opus.subtitles;


import com.github.jmkgreen.morphia.Datastore;
import com.github.jmkgreen.morphia.Morphia;
import com.github.jmkgreen.morphia.query.Query;
import com.github.jmkgreen.morphia.query.UpdateOperations;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.List;


public class CorporaDB {

	private final static String dbName = "corpora";
	private final static String dbLocation = "localhost";
	private final static int dbPort = 27017;

	private static CorporaDB db;

	private Datastore ds;

	
	public static CorporaDB getInstance() {
		if (db == null) {
			db = new CorporaDB();
		}
		return db;
	}
	
	@SuppressWarnings("deprecation")
	private CorporaDB() {
		try {
			Morphia morphia = new Morphia();
			Mongo mongo = new Mongo(dbLocation, dbPort);
			ds = morphia.createDatastore(mongo, dbName);
			morphia.map(SentenceGroup.class);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void saveSentenceGroup(SentenceGroup g) {
		ds.save(g);
	}
	

	public List<SentenceGroup> getAllEntries() {
		Query<SentenceGroup> q = ds.createQuery(SentenceGroup.class);
		return q.asList();
	}
	
	public Iterator<SentenceGroup> getEntriesIterator() {
		Query<SentenceGroup> q = ds.createQuery(SentenceGroup.class);
		return q.iterator();
	}
	
	public void update(SentenceGroup e, String field, List<Object> values) {
		Query<SentenceGroup> updateQuery = ds
				.createQuery(SentenceGroup.class).field("_id")
				.equal(e.getId()); 
		UpdateOperations<SentenceGroup> ops = ds.createUpdateOperations(
				SentenceGroup.class).addAll(field, values, false);
		ds.update(updateQuery, ops);
	}
	
	public void update(SentenceGroup e, String field, Object value) {
		Query<SentenceGroup> updateQuery = ds
				.createQuery(SentenceGroup.class).field("_id")
				.equal(e.getId());
		UpdateOperations<SentenceGroup> ops = ds.createUpdateOperations(
				SentenceGroup.class).add(field, value);
		ds.update(updateQuery, ops);
	}
}

package max.nlp.dal.generic;


import java.net.UnknownHostException;
import java.util.List;

import max.nlp.dal.wiktionary.types.Text;
import max.nlp.dal.wiktionary.types.Translation;

import com.github.jmkgreen.morphia.Datastore;
import com.github.jmkgreen.morphia.Morphia;
import com.github.jmkgreen.morphia.query.Query;
import com.mongodb.Mongo;
import com.mongodb.MongoException;


public class GenericDB {

	private final static String dbName = "data";
	private final static String dbLocation = "localhost";
	private final static int dbPort = 27017;

	private static GenericDB db;

	private Datastore ds;

	
	
	public static GenericDB getInstance() {
		if (db == null) {
			db = new GenericDB();
		}
		return db;
	}
	
	
	private GenericDB() {
		try {
			Morphia morphia = new Morphia();
			@SuppressWarnings("deprecation")
			Mongo mongo = new Mongo(dbLocation, dbPort);
			ds = morphia.createDatastore(mongo, dbName);
			morphia.map(Text.class);
			morphia.map(Translation.class);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void saveEntry(Translation e) {
		ds.save(e);
	}
	
	public void saveEntry(Text e) {
		ds.save(e);
	}
	
	public List<Translation> getAllTranslations(){
		Query<Translation> q = ds.createQuery(Translation.class);
		return q.asList();
	}
}

package max.nlp.dal.opus.europarl;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;

public class EuroparlDB {

	private static EuroparlDB self;
	private DB db;
	private DBCollection sentences, alignment;

	public List<DBCollection> getAlignmentCollections() {
		List<DBCollection> colls = new ArrayList<DBCollection>();
		Set<String> collections = db.getCollectionNames();

		for (String collectionName : collections) {
			colls.add(db.getCollection(collectionName));
		}
		return colls;
	}

	public List<DBCollection> getEnglishAlignmentCollections() {
		List<DBCollection> colls = new ArrayList<DBCollection>();
		Set<String> collections = db.getCollectionNames();

		for (String collectionName : collections) {
			if (collectionName.contains("en"))
				colls.add(db.getCollection(collectionName));
		}
		return colls;
	}

	public void saveAlignment(String cName, SentenceAlignment alignment) {
		db.getCollection(cName).save(alignment);
	}

	private EuroparlDB() {
		try {
			MongoClient mongoClient = new MongoClient("localhost", 27017);
			db = mongoClient.getDB("europarl");
			sentences = db.getCollection("sentences");
			alignment = db.getCollection("alignments");
			ensureIndeces();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void ensureIndeces() {
		sentences.ensureIndex(new BasicDBObject("_id", 1));

		sentences.ensureIndex(new BasicDBObject("key", 1));
		sentences.ensureIndex(new BasicDBObject("lang", 1));
		sentences.ensureIndex(new BasicDBObject("line", 1));
		sentences.ensureIndex(new BasicDBObject("key", 1).append("lang", 1)
				.append("line", 1));
		sentences.ensureIndex(new BasicDBObject("key", 1).append("lang", 1));
		sentences.ensureIndex(new BasicDBObject("key", 1).append("line", 1));
		alignment.ensureIndex(new BasicDBObject("sentences", 1));

	}

	public static EuroparlDB getInstance() {
		if (self == null) {
			self = new EuroparlDB();
		}
		return self;
	}

	public void saveSentence(EuroparlSentence s) {
		WriteResult r = sentences.save(s);
	}

	public Iterator<DBObject> getSentenceIterator() {
		DBCursor res = sentences.find();
		return res.iterator();
	}

	public Iterator<DBObject> getSentenceIterator(String language) {
		DBCursor res = sentences.find(new BasicDBObject("lang", language));
		return res.iterator();
	}

	public void updateSentence(String lang, String key, int line, String field,
			String value) {
		BasicDBObject query = new BasicDBObject();
		query.put("lang", lang);
		query.put("key", key);
		query.put("line", line);
		WriteResult res = sentences.update(query, new BasicDBObject("$set",
				new BasicDBObject(field, value)));
	}

	public Iterator<DBObject> findSentencesWithText() {
		DBObject query = new BasicDBObject("sentence", new BasicDBObject(
				"$exists", true));
		return sentences.find(query).iterator();
	}

	public void updateSentence(ObjectId id, String field, Object value) {
		BasicDBObject query = new BasicDBObject();
		query.put("_id", id);
		WriteResult res = sentences.update(query, new BasicDBObject("$set",
				new BasicDBObject(field, value)));
	}

	public List<EuroparlSentence> findSentencesForLine(String key, Integer line) {
		BasicDBObject query = new BasicDBObject();

		query.put("key", key);
		query.put("line", line);
		DBCursor results = sentences.find(query);
		List<DBObject> obj = results.toArray();
		List<EuroparlSentence> sens = new ArrayList<EuroparlSentence>();

		for (DBObject dbo : obj) {
			sens.add(new EuroparlSentence(dbo));
		}
		return sens;
	}

	public EuroparlSentence findSentenceForLineInLang(String language,
			String key, Integer line) {
		BasicDBObject query = new BasicDBObject();

		query.put("key", key);
		query.put("line", line);
		query.put("lang", language);
		DBObject dbo = sentences.findOne(query);
		if (dbo == null) {
			return null;
		} else
			return new EuroparlSentence(dbo);
	}

	public Iterator<DBObject> findSentencesWithText(String lang) {
		DBObject query = new BasicDBObject();

		query.put("lang", lang);
		query.put("sentence", new BasicDBObject("$exists", true));
		return sentences.find(query).iterator();
	}

	public Iterator<DBObject> findSentencesWithIdioms(String lang) {
		DBObject query = new BasicDBObject();

		query.put("lang", lang);
		query.put("matchingIdioms", new BasicDBObject("$exists", true));
		return sentences.find(query).iterator();
	}

	public EuroparlSentence findSentence(ObjectId id) {
		DBObject query = new BasicDBObject();

		query.put("_id", id);
		return new EuroparlSentence(sentences.findOne(query));
	}

	public List<EuroparlSentence> findSentencesForIdiom(ObjectId idiomId) {
		DBObject query = new BasicDBObject();
		List<EuroparlSentence> matchingSentences = new ArrayList<EuroparlSentence>();
		query.put("matchingIdioms", idiomId);
		List<DBObject> sens = sentences.find(query).toArray();
		for (DBObject uncastSentence : sens) {
			matchingSentences.add(new EuroparlSentence(uncastSentence));
		}
		return matchingSentences;
	}

	public static void main(String[] args) {

		EuroparlDB a = EuroparlDB.getInstance();
		List<EuroparlSentence> s = a.findMatchingSentences(new ObjectId(
				"52b8db4ce4b088537aa41cbc"));
		for (EuroparlSentence b : s) {
			System.out.println(b);
		}

	}

	// public void updateOrCreateAlignment(SentenceAlignment alignedSentences) {
	//
	// // should chekc to see if an alignment with any of the values in this
	// // one is found. if not, make new one, otherwise update that one .
	// BasicDBObject query = new BasicDBObject();
	// BasicDBList in = new BasicDBList();
	// for (String sentenceID : alignedSentences.getSentences()) {
	// in.add(sentenceID);
	// }
	// query.put("$in", in);
	// DBCursor find = alignment.find(new BasicDBObject("sentences", query));
	// List<DBObject> results = find.toArray();
	// if (results.size() == 0) {
	// saveAlignment(alignedSentences);
	// } else {
	// for (DBObject result : results) {
	// SentenceAlignment oldAlignment = new SentenceAlignment(result);
	// for (String newSentence : alignedSentences.getSentences()) {
	// updateAlignment(oldAlignment, newSentence);
	// }
	//
	// }
	// }
	//
	// }

	public void saveAlignment(SentenceAlignment a) {
		alignment.save(a);
	}

	public void updateAlignment(SentenceAlignment a, String newAlignment) {
		BasicDBObject query = new BasicDBObject();
		query.put("_id", (ObjectId) a.get("_id"));
		DBObject update = new BasicDBObject("$addToSet", new BasicDBObject(
				"sentences", newAlignment));

		alignment.update(query, update);
	}

	public List<EuroparlSentence> findMatchingSentences(ObjectId id) {

		Iterator<DBObject> itr = alignment.find(
				new BasicDBObject("sentences", id.toString())).iterator();
		List<SentenceAlignment> alignments = new ArrayList<SentenceAlignment>();
		while (itr.hasNext()) {
			SentenceAlignment s = new SentenceAlignment(itr.next());
			System.out.println(s);
			alignments.add(s);
		}

		ArrayList<EuroparlSentence> matchingSentences = new ArrayList<EuroparlSentence>();
		for (SentenceAlignment matchingAlignments : alignments) {
			for (ObjectId sentenceID : matchingAlignments.getSentences()) {
				// if (!sentenceID.equals(id.toString()))
				matchingSentences.add(findSentence(sentenceID));
			}
		}
		return matchingSentences;

	}

}

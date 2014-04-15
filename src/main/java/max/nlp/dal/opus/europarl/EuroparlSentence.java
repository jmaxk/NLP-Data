package max.nlp.dal.opus.europarl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import max.nlp.dal.wiktionary.WiktionaryDB;
import max.nlp.dal.wiktionary.types.WiktionaryEntry;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class EuroparlSentence extends BasicDBObject {

	//

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((getKey() == null) ? 0 : getKey().hashCode());
		result = prime * result
				+ ((getLang() == null) ? 0 : getLang().hashCode());
		result = prime * result + getLine().hashCode();
		result = prime * result
				+ ((getSentence() == null) ? 0 : getSentence().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		EuroparlSentence other = (EuroparlSentence) obj;
		if (getKey() == null) {
			if (other.getKey() != null)
				return false;
		} else if (!getKey().equals(other.getKey()))
			return false;
		if (getLang() == null) {
			if (other.getLang() != null)
				return false;
		} else if (!getLang().equals(other.getLang()))
			return false;
		if (!getLine().equals(other.getLine()))
			return false;
		if (getSentence() == null) {
			if (other.getSentence() != null)
				return false;
		} else if (!getSentence().equals(other.getSentence()))
			return false;
		return true;
	}

	public ObjectId getId() {
		return (ObjectId) get("_id");
	}

	public final static String keyField = "key";

	public void setKey(String key) {
		put(keyField, key);
	}

	public String getKey() {
		Object val = get(keyField);
		if (val == null)
			return null;
		else
			return (String) val;
	}

	public final static String langField = "lang";

	public void setLang(String lang) {
		put(langField, lang);
	}

	public String getLang() {
		Object val = get(langField);
		if (val == null)
			return null;
		else
			return (String) val;
	}

	public final static String lineField = "line";

	public void setLine(Integer line) {
		put(lineField, line);
	}

	public Integer getLine() {
		Object val = get(lineField);
		if (val == null)
			return null;
		else
			return (Integer) val;
	}

	public EuroparlSentence(DBObject dbo) {
		super(dbo.toMap());
	}
	

	public EuroparlSentence() {

	}

	public EuroparlSentence(String line) {
		String[] parts = line.split("/");
		setLang(parts[0]);
		setKey(parts[1]);
	}

	public final static String sentenceField = "sentence";

	public void setSentence(String sentence) {
		put(sentenceField, sentence);
	}

	public String getSentence() {
		Object val = get(sentenceField);
		if (val == null)
			return null;
		else
			return (String) val;
	}

	public String toString() {
		return super.toString();
	}

	public final static String matchingIdiomsField = "matchingIdioms";

	public void setMatchingIdioms(List<ObjectId> matchingIdioms) {
		put(matchingIdiomsField, matchingIdioms);
	}

	public List<ObjectId> getMatchingIdioms() {
		BasicDBList items = (BasicDBList) get(matchingIdiomsField);

		List<ObjectId> usableItems = new ArrayList<ObjectId>();
		if (items == null || items.isEmpty())
			return usableItems;
		else {
			for (Object dbo : items) {
				usableItems.add((ObjectId) dbo);
			}
			return usableItems;
		}

	}

	public List<WiktionaryEntry> convertEntryIDsToEntries() {
		List<ObjectId> idioms = getMatchingIdioms();
		WiktionaryDB wdb = WiktionaryDB.getInstance();

		List<WiktionaryEntry> entries = new ArrayList<WiktionaryEntry>();
		if (idioms == null || idioms.size() == 0)
			return entries;
		for (ObjectId id : idioms) {
			WiktionaryEntry entry = wdb.findIdiom(id);
			entries.add(entry);
		}

		return entries;
	}
	
	

}

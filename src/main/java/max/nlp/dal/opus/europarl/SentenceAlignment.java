package max.nlp.dal.opus.europarl;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class SentenceAlignment extends BasicDBObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final static String sentencesField = "sentences";

	public void setSentences(List<ObjectId> sentences) {
		put(sentencesField, sentences);
	}
	
	public SentenceAlignment(DBObject dbo) {
		super(dbo.toMap());
	} 

	
	public SentenceAlignment() {
	} 
	@SuppressWarnings("unchecked")
	public List<ObjectId> getSentences() {
		Object ret = get(sentencesField);

		if (ret instanceof List<?>)
			return (List<ObjectId>) ret;
		else {
			BasicDBList items = (BasicDBList) get(sentencesField);

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

	}
}

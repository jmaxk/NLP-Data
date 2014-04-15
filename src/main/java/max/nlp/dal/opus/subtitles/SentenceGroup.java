package max.nlp.dal.opus.subtitles;


import com.github.jmkgreen.morphia.annotations.Entity;
import com.github.jmkgreen.morphia.annotations.Id;
import com.github.jmkgreen.morphia.annotations.Reference;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import max.nlp.dal.wiktionary.types.WiktionaryEntry;

import org.bson.types.ObjectId;

@Entity("SentenceGroup")
public class SentenceGroup {

	@Id
    ObjectId id;
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}
	HashMap<String, String> sentences = new HashMap<String,String>();

	public HashMap<String, String> getSentences() {
		return sentences;
	}
	
	public String getSentence(String language){
		return sentences.get(language);
	}

	public void setSentences(HashMap<String, String> sentences) {
		this.sentences = sentences;
	}
	public void addSentence(String language, String sentence){
		sentences.put(language, sentence);
	}
	
	//this may refer to a wiktionaryEntry
	@Reference
    private List<WiktionaryEntry> entries = new ArrayList<WiktionaryEntry>();
	public List<WiktionaryEntry> getEntries() {
		return entries;
	}

	public void setEntries(List<WiktionaryEntry> entry) {
		this.entries = entry;
	}
	
	public void addEntry(WiktionaryEntry entry){
		entries.add(entry);
	}
	
}

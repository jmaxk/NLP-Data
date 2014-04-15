package max.nlp.dal.wiktionary.types;

import java.util.ArrayList;

import org.bson.types.ObjectId;

import com.github.jmkgreen.morphia.annotations.Entity;
import com.github.jmkgreen.morphia.annotations.Id;

@Entity("Text")
public class Text {
	@SuppressWarnings("unused")
	@Id
	private ObjectId id;
	private String word;
	private String gender;
	private String number;
	private String language;
	private boolean isMultipleWords;

	public boolean isMultipleWords() {
		return isMultipleWords;
	}

	public void setMultipleWords(boolean isMultipleWords) {
		this.isMultipleWords = isMultipleWords;
	}

	private ArrayList<String> modifiers = new ArrayList<String>();

	public ArrayList<String> getModifiers() {
		return modifiers;
	}

	public void setModifiers(ArrayList<String> modifiers) {
		this.modifiers = modifiers;
	}

	@Override
	public String toString() {
		return "Text [word=" + word + ", gender=" + gender + ", number="
				+ number + ", language=" + language + "]";
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Text(String word) {

		this.word = word;
	}

	public Text(String word, String lang) {

		this.word = word;
		this.language = lang;
	}

	public Text() {
	}
}

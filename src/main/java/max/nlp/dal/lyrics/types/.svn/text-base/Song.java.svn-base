package max.nlp.dal.lyrics.types;


import com.github.jmkgreen.morphia.annotations.Entity;
import com.github.jmkgreen.morphia.annotations.Index;
import com.github.jmkgreen.morphia.annotations.Indexes;

@Entity
@Indexes( @Index("author, -title"))
public class Song {

	public Song(){
		
	}
	private String language;
	private String text;
	private String link;
    private String author;
	private String title;
    private String translator;
    private String numVotes;
    private String rating;
    
    
    public Song(String language, String text, String link, String author, String title, String translator) {
		super();
		this.language = language;
		this.text = text;
		this.link = link;
		this.author = author;
		this.title = title;
		this.translator = translator;
	}
    
    
    public String getNumVotes() {
		return numVotes;
	}

	public void setNumVotes(String numVotes) {
		this.numVotes = numVotes;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTranslator() {
		return translator;
	}

	public void setTranslator(String translator) {
		this.translator = translator;
	}

	public String getLanguage() {
		return language;
	}
	@Override
	public String toString() {
		return "Song [language=" + language + ", text=" + text + ", link="
				+ link + ", author=" + author + ", title=" + title
				+ ", translator=" + translator + "]";
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
}

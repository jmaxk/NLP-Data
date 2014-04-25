package max.nlp.dal.yelp;

public class Review {

	private String text;

	public Review(String text) {
		super();
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}

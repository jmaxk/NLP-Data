package max.nlp.dal.types.statistics;

import com.mongodb.BasicDBObject;

public class Text extends BasicDBObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}

package max.nlp.dal.annotations;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class Annotation extends BasicDBObject {

	private static final long serialVersionUID = 1L;
	public final static String textField = "text";

	public void settext(String text) {
		put(textField, text);
	}

	public String getText() {
		Object val = get(textField);
		if (val == null)
			return null;
		else
			return (String) val;
	}
	
	public Annotation(DBObject dbo) {
		super(dbo.toMap());
	}
	public Annotation(){}
}

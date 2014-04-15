package max.nlp.dal.annotations;

import com.mongodb.DBObject;

public class DBpediaAnnotation extends Annotation {

	private static final long serialVersionUID = 1L;
	public final static String startOffsetField = "startOffset";

	public void setStartOffset(int startOffset) {
		put(startOffsetField, startOffset);
	}

	public Integer getStartOffset() {
		Object val = get(startOffsetField);
		if (val == null)
			return null;
		else
			return (Integer) val;
	}

	public final static String endOffsetField = "endOffset";

	public void setendOffset(int endOffset) {
		put(endOffsetField, endOffset);
	}

	public Integer getEndOffset() {
		Object val = get(endOffsetField);
		if (val == null)
			return null;
		else
			return (Integer) val;
	}

	public final static String uriField = "uri";

	public void setUri(String uri) {
		put(uriField, uri);
	}

	public String getUri() {
		Object val = get(uriField);
		if (val == null)
			return null;
		else
			return (String) val;
	}

	public DBpediaAnnotation(DBObject dbo) {
		super(dbo);
	}

	public DBpediaAnnotation() {
		super();
	}

	@Override
	public String toString() {
		return "DBpediaAnnotation [start=" + getStartOffset()
				+ ", end=" + getEndOffset() + ", uri="
				+ getUri() + "]";
	}
	
	
}

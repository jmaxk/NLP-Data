package max.nlp.dal.types.statistics;

import java.util.List;

public class TaggedText {

	private String text;
	private String pos;
	public TaggedText(String text, String pos) {
		this.text = text;
		this.pos = pos;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pos == null) ? 0 : pos.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaggedText other = (TaggedText) obj;
		if (pos == null) {
			if (other.pos != null)
				return false;
		} else if (!pos.equals(other.pos))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "TaggedText [text=" + text + ", pos=" + pos + "]";
	}
	public String getPos() {
		return pos;
	}
	public void setPos(String pos) {
		this.pos = pos;
	}
	
	public static String toText(List<TaggedText> text){
		StringBuilder sb = new StringBuilder();
		for(TaggedText t :text ){
			sb.append(t.getText() + " ");
		}
		return sb.toString();
	}
}

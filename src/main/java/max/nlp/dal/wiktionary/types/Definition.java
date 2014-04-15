package max.nlp.dal.wiktionary.types;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Definition {


	private String definition;

	public Definition() {
	}

	private static Pattern CONTEXT_PATTERN = Pattern
			.compile("\\{\\{context\\|(.*)\\|.*\\}\\}");

	public boolean isIdiomatic() {
		return idiomatic;
	}

	public void setIdiomatic(boolean idiomatic) {
		this.idiomatic = idiomatic;
	}

	public Definition(String definition) {
		Matcher m = CONTEXT_PATTERN.matcher(definition);
		while (m.find()) {
			if (m.group(1).contains("idiom"))
				idiomatic = true;
		}
		this.definition = m.replaceAll("").replaceAll("# ", "");
	}

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public List<String> getAssociatedText() {
		return associatedText;
	}

	public void setAssociatedText(List<String> associatedText) {
		this.associatedText = associatedText;
	}

	@Override
	public String toString() {
		return "Definition [definition=" + definition + ", idiomatic="
				+ idiomatic + "]";
	}

	private boolean idiomatic = false;
	private List<String> associatedText;
}

package max.nlp.dal.wiktionary.types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;

import com.github.jmkgreen.morphia.annotations.Entity;
import com.github.jmkgreen.morphia.annotations.Id;
import com.github.jmkgreen.morphia.annotations.Indexed;
import com.github.jmkgreen.morphia.annotations.Transient;

@Entity(noClassnameStored = true)
public class WiktionaryEntry {
	
	@Indexed
	private boolean isIdiomatic;
	public boolean isIdiomatic() {
		return isIdiomatic;
	}

	public void setIdiomatic(boolean isIdiomatic) {
		this.isIdiomatic = isIdiomatic;
	}

	public List<Definition> getDefinitions() {
		return definitions;
	}

	public void setDefinitions(List<Definition> definitions) {
		this.definitions = definitions;
	}

	public List<String> getCoordinateTerms() {
		return coordinateTerms;
	}

	public void setCoordinateTerms(List<String> coordinateTerms) {
		this.coordinateTerms = coordinateTerms;
	}

	public List<String> getAntonyms() {
		return antonyms;
	}

	public void setAntonyms(List<String> antonyms) {
		this.antonyms = antonyms;
	}

	public List<InterwikiLink> getLinks() {
		return links;
	}

	public void setLinks(List<InterwikiLink> links) {
		this.links = links;
	}

	static Logger log = Logger.getLogger(WiktionaryEntry.class);
	@Id
	ObjectId id;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	private List<String> forms = new ArrayList<String>();
	private String etymology; // ===Etymology
	@Indexed	private String idiom;
	private String POS;
	@Indexed private List<String> synonyms = new ArrayList<String>();
	private String language = null;

	private List<Definition> definitions = new ArrayList<Definition>();
	private List<String> alternativeForms = new ArrayList<String>();
	private List<String> derivedTerms = new ArrayList<String>();
	private List<String> coordinateTerms = new ArrayList<String>();
	private List<Text> relatedTerms = new ArrayList<Text>();
	@Indexed private List<String> antonyms = new ArrayList<String>();
	private List<WiktionaryTranslation> translations = new ArrayList<WiktionaryTranslation>();
	private List<InterwikiLink> links = new ArrayList<InterwikiLink>();

	@Transient
	private Pattern SQUARE_BRACKET_PATTERN = Pattern
			.compile("\\[\\[([^\\]]*)\\]\\]+");
	@Transient
	private static Pattern INTERWIKI_LINK_PATTERN = Pattern
			.compile("\\[\\[([a-zA-z]{2}):(.*)\\]\\]+");

	public List<String> getForms() {
		return forms;
	}

	public void setForms(List<String> forms) {
		this.forms = forms;
	}

	public void addForm(String form) {
		forms.add(form);
	}

	public String getPOS() {
		return POS;
	}

	public void setPOS(String POS) {
		this.POS = POS;
	}

	public String getEtymology() {
		return etymology;
	}

	public void setEtymology(String etymology) {
		this.etymology = etymology;
	}

	public String getIdiom() {
		return idiom;
	}

	public void setIdiom(String name) {
		this.idiom = name;
	}

	public List<String> getSynonyms() {
		return synonyms;
	}

	public void setSynonyms(List<String> synonyms) {
		this.synonyms = synonyms;
	}

	public List<String> getAlternativeForms() {
		return alternativeForms;
	}

	public void setAlternativeForms(List<String> alternativeForms) {
		this.alternativeForms = alternativeForms;
	}

	public List<String> getDerivedTerms() {
		return derivedTerms;
	}

	public void setDerivedTerms(List<String> derivedTerms) {
		this.derivedTerms = derivedTerms;
	}

	public List<Text> getRelatedTerms() {
		return relatedTerms;
	}

	public void setRelatedTerms(List<Text> relatedTerms) {
		this.relatedTerms = relatedTerms;
	}

	public List<WiktionaryTranslation> getTranslations() {
		return translations;
	}

	public void setTranslations(List<WiktionaryTranslation> translations) {
		this.translations = translations;
	}

	@Override
	public String toString() {
		return "WiktionaryEntry \n[forms=" + forms + "\n etymology="
				+ etymology + "\n  idiom=" + idiom + "\n POS=" + POS
				+ "\n synonyms=" + synonyms + "\n definitions=" + definitions
				+ "\n alternativeForms=" + alternativeForms
				+ "\n derivedTerms=" + derivedTerms + "\n coordinateTerms="
				+ coordinateTerms + "\n relatedTerms=" + relatedTerms
				+ "\n antonyms=" + antonyms + "\n translations=" + translations
				+ "\n links=" + links + "]";
	}

	public WiktionaryEntry() {
	}

	private ArrayList<String> cleanSectionAndBreakIntoLines(String section) {
		String[] content = section.split("\n");
		ArrayList<String> cleanContent = new ArrayList<String>();
		for (String line : content) {
			if (line != null && !line.isEmpty() && !line.equals("\n")
					&& !line.equals("")) {
				cleanContent.add(line);
			}
		}
		return cleanContent;
	}

	public WiktionaryEntry(Section relevantSection, Section root) {
		this.setIdiom(root.getHeading());

		links = extractInterwikiLinks(root.getContent());

		for (Section section : relevantSection.getAllSubSectionsInclusive()) {
			String sectionHeader = section.getHeading();
			String sectionContent = section.getContent();
			ArrayList<String> sectionContentByLine = cleanSectionAndBreakIntoLines(sectionContent);
			if (sectionHeader.contains("Noun")
					|| sectionHeader.contains("Adjective")
					|| sectionHeader.contains("Verb")
					|| sectionHeader.contains("Prepositional phrase")
					|| sectionHeader.contains("Phrase")
					|| sectionHeader.contains("Adverb")
					|| sectionHeader.contains("Pronoun")
					|| sectionHeader.contains("Proper noun")
					|| sectionHeader.contains("Interjection")

					|| sectionHeader.contains("Conjunction")
					|| sectionHeader.contains("Interjection")
					|| sectionHeader.contains("Preposition")
					|| sectionHeader.contains("Determiner")
					|| sectionHeader.contains("Article")
					|| sectionHeader.contains("Idiom")
					|| sectionHeader.contains("Proverb")
					|| sectionHeader.contains("Abbreviation")
					|| sectionHeader.contains("Postposition")
					|| sectionHeader.contains("Acronym")
					|| sectionHeader.contains("Expression")) {
				this.setPOS(sectionHeader);
				HashMap<String, List<String>> termDictionary = new HashMap<String, List<String>>();
				String definition = "";
				List<String> sections = new ArrayList<String>();
				for (String line : sectionContentByLine) {
					if (line.startsWith("{{") || line.startsWith("'''")) {
						String[] subsections = line.split("\\|");
						if (subsections != null) {
							for (String s : subsections) {
								String[] temp = s.split("=");
								if (temp.length > 1)
									this.addForm(temp[1]
											.replaceAll("\\[\\[", "")
											.replaceAll("\\]\\]", "")
											.replaceAll("\\}\\}", "")
											.replaceAll("\\{\\{idiomatic", ""));
							}
						}
					} else if (line.startsWith("# ")) {

						definition = line;
						sections = new ArrayList<String>();
						termDictionary.put(definition, sections);

					} else {
						if (!line.isEmpty()) {
							List<String> terms = termDictionary.get(definition);
							if (terms == null)
								terms = new ArrayList<String>();
							if (line.contains(this.getIdiom()))
								terms.add(line);
							termDictionary.put(definition, terms);
						}
					}
				}
				termDictionary.put(definition, sections);

				// convert term dictionary to definitions so morphia can store
				for (Entry<String, List<String>> e : termDictionary.entrySet()) {
					Definition d = new Definition(e.getKey());
					d.setAssociatedText(e.getValue());
					definitions.add(d);
				}
			}

			else if (sectionHeader.contains("Etymology")) {
			}

			else if (sectionHeader.contains("Translations")
					|| sectionHeader.contains("Translation")) {
				translations = WiktionaryTranslation.fromTable(sectionContent);
			}

			else if (sectionHeader.contains("Coordinate term")
					|| sectionHeader.contains("Coordinate terms")) {
				coordinateTerms.addAll(extractSquareBrackets(sectionContent));
			}

			else if (sectionHeader.contains("Derived terms")) {
				derivedTerms.addAll(extractSquareBrackets(sectionContent));

			}

			/* DONE */else if (sectionHeader.contains("Related terms")) {
				for (String line : sectionContentByLine) {
					Pattern p = Pattern
							.compile("\\[\\[([A-Za-z]{2}):(.*)\\]\\]");
					Matcher m = p.matcher(line);
					String relatedTerm = "";
					String language = "";
					while (m.find()) {
						language = m.group(1);
						relatedTerm = m.group(2);
						// make interwiki link representation
					}
					relatedTerms.add(new Text(relatedTerm, language));
					
					if (relatedTerm.isEmpty()) { // wanst link
						line = line.replaceAll("\\<.*\\>", "");

						if (!relatedTerm.startsWith("Category")) {
							relatedTerms.add(new Text(relatedTerm, language));
						}
						if (line.contains("[["))
							relatedTerm = line.replaceAll("[^A-Za-z ]", "");

						p = Pattern.compile("\\{\\{l\\|.*\\|(.*)\\}\\}");
						m = p.matcher(line);
						while (m.find()) {
							relatedTerm = m.group(1);
						}
						relatedTerms.add(new Text(relatedTerm, language));
					}
					if (relatedTerm.isEmpty() && line != null
							&& !line.isEmpty()) {
						log.info("[Related term] " + relatedTerm + " unparsed");
					}

				}
			} else if (sectionHeader.contains("Synonyms")) {
				synonyms.addAll(extractSquareBrackets(section.getContent()));

			} else if (sectionHeader.contains("Alternative forms")
					|| sectionHeader.contains("Alternate forms")) {
				alternativeForms.addAll(extractSquareBrackets(sectionContent));

			} else if (sectionHeader.contains("Antonyms")) {
				antonyms.addAll(extractSquareBrackets(sectionContent));
			}

			// UNIMPLEMENTED
			else if (sectionHeader.contains("Anagrams")) {
			} else if (sectionHeader.contains("Inflection")) {
			} else if (sectionHeader.contains("Usage notes")) {
			} else if (sectionHeader.contains("Pronunciation")) {
			} else if (sectionHeader.contains("See also")) {
			} else if (sectionHeader.contains("Quotations")) {
			} else if (sectionHeader.contains("Declension")) {
			} else if (sectionHeader.contains("Idiom")) {
			} else if (sectionHeader.contains("References")) {
			} else if (sectionHeader.contains("Proverb")) {
			} else if (sectionHeader.contains("Number")) {
			} else if (sectionHeader.contains("External links")) {
			} else if (sectionHeader.contains("External links")) {
			} else if (sectionHeader.contains("Hypernym")) {
			} else if (sectionHeader.contains("Hyponyms")) {
			} else if (sectionHeader.contains("Conjugation")) {
			} else if (sectionHeader.contains("Compounds")) {
			} else if (sectionHeader.contains("Abbreviations")) {
			} else if (sectionHeader.contains("Descendants")) {
			} else if (sectionHeader.contains("(Conjunction)")) {
			} else if (sectionHeader.contains("(Initialism)")) {
			} else if (sectionHeader.contains("(Determiner)")) {
			} else {
				// System.out.println("                    else if (sectionHeader.contains(\""
				// + sectionHeader + "\"" + ")){");
			}
		}
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public List<String> extractSquareBrackets(String line) {
		// TODO Match * {{sense|exclamation of praise}} [[bravo]], [[encore]]
		// TODO match interwiki links [et:some page]
		List<String> content = new ArrayList<String>();
		Matcher m = SQUARE_BRACKET_PATTERN.matcher(line);
		if (m.find()) {
			while (m.find()) {
				content.add(m.group(1));
			}

		} else {
			log.info(line + " unparsed");

		}
		return content;
	}

	public List<InterwikiLink> extractInterwikiLinks(String line) {

		List<InterwikiLink> links = new ArrayList<InterwikiLink>();
		Matcher m = INTERWIKI_LINK_PATTERN.matcher(line);
		while (m.find()) {

			InterwikiLink link = new InterwikiLink(m.group(1), m.group(2));
			links.add(link);
		}

		return links;
	}
}

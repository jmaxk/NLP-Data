package max.nlp.dal.wiktionary.types;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WiktionaryTranslation {
	private ArrayList<Text> translationsList = new ArrayList<Text>();

	public WiktionaryTranslation(){}
	@SuppressWarnings("unused")
	private ArrayList<String> raw = new ArrayList<String>();
	private String roughTranslation = "unk";

	public static List<WiktionaryTranslation> fromTable(String rawContent) {
		String[] lines = rawContent.split("\\n");
		List<WiktionaryTranslation> translations = new ArrayList<WiktionaryTranslation>();
		ArrayList<String> table = new ArrayList<String>();

		boolean inTable = false;
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];
			if (line.startsWith("{{trans-top")) {
				inTable = true;
			} else if (line.startsWith("{{trans-bottom")) {
				WiktionaryTranslation t = new WiktionaryTranslation(table);
				t.raw = table;
				translations.add(t);
				table = new ArrayList<String>();
				inTable = false;

			}
			if (inTable)
				table.add(line);
		}
		return translations;
	}

	private WiktionaryTranslation(ArrayList<String> table) {
		ArrayList<String> modifiers = new ArrayList<String>();
		for (String line : table) {
			if (!line.isEmpty() && !line.equals("\n") && !line.equals("")) {
				if (line.startsWith("{{trans-top")) {
					this.roughTranslation = line.replaceAll("\\{\\{trans-top|",
							"").replaceAll("\\}\\}", "");
				} else if (line.startsWith("* ") || line.startsWith("*: ")) {
					int wordsAdded = 0;
					// Extract the language of the translations
					String language = "";
					Pattern languagePattern = Pattern
							.compile("\\*:? [A-Za-z]*:");
					Matcher languageFinder = languagePattern.matcher(line);
					while (languageFinder.find()) {
						language = languageFinder.group().replaceAll("[^A-Za-z]","");
					}

					line = line.replaceAll("\\*.*[A-Za-z].*:", "");
					ArrayList<String> fixedGroups = new ArrayList<String>();
					if (line.contains("}},")) {
						String[] groups = line.split("}},");
						for (String g : groups) {
							fixedGroups.add(g + "}}");
						}
					} else
						fixedGroups.add(line);

					for (String group : fixedGroups) {
						// extract all the words
						Pattern translationPattern = Pattern
								.compile("\\{\\{(.*?)\\}\\}");
						Matcher matcher = translationPattern.matcher(group);
						while (matcher.find()) {
							String field = matcher.group(1);
							if (field.startsWith("qualifier")) {
								modifiers.add(field);
							} else if (field.startsWith("t")
									|| field.startsWith("l")) {
								String[] translation = field.split("\\|");
								if (translation.length > 2) {
									Text w = new Text(translation[2]);
									w.setModifiers(modifiers);
									w.setLanguage(language);
									translationsList.add(w);
									modifiers = new ArrayList<String>();
									wordsAdded++;

								} else {
									// System.out.println("debug " +
									// matcher.group(1));
								}

							}
						}
					}
					// try using bracket matchers
					if (wordsAdded == 0) {
						Pattern translationPattern = Pattern
								.compile("\\[\\[(.*?)\\]\\]");
						Matcher matcher = translationPattern.matcher(line);
						while (matcher.find()) {
							String field = matcher.group(1);
							Text w = new Text(field);
							w.setLanguage(language);

							translationsList.add(w);
							wordsAdded++;
						}
					}

					// maybe it is just words
					if (wordsAdded == 0) {
						if (!line.contains("{{") && !line.contains("[[")
								&& !line.equals("")) {
							Text w = new Text(line);
							w.setLanguage(language);

							translationsList.add(w);
							wordsAdded++;
						}
					}

					// still can't figure itout, for debug
					// if (wordsAdded == 0) {
					// if (!line.equals(""))
					// System.out.println(line);
					// }

				}
			}
		}
	}

	public ArrayList<Text> getTranslationsList() {
		return translationsList;
	}

	public void setTranslationsList(ArrayList<Text> translationsList) {
		this.translationsList = translationsList;
	}

	public String getRoughTranslation() {
		return roughTranslation;
	}

	public void setRoughTranslation(String roughTranslation) {
		this.roughTranslation = roughTranslation;
	}

	@Override
	public String toString() {
		return "Translation [translationsList=" + translationsList
				+ ", roughTranslation=" + roughTranslation + "]";
	}


}

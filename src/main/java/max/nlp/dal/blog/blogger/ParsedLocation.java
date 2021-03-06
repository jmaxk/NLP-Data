package max.nlp.dal.blog.blogger;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class ParsedLocation extends BasicDBObject {

	private static final long serialVersionUID = 1L;
	public final static String countryField= "country";

	public void setCountry(String val) {
		put(countryField, val);
	}

	public String getCountry() {
		return (String) get(countryField);
	}

	public final static String cityField = "city";

	public void setCity(String val) {
		put(cityField, val);
	}

	public String getCity() {
		return (String) get(cityField);
	}

	public final static String stateField = "state";

	public void setState(String val) {
		put(stateField, val);
	}

	public String getState() {
		Object ret = get(stateField);
		if (ret == null)
			return "";
		else
			return (String) ret;
	}

	public ParsedLocation(DBObject dbo) {
		super(dbo.toMap());
	}

	public ParsedLocation() {

	}

	@Override
	public String toString() {
		return "ParsedLocation [country=" + getCountry() + ", city="
				+ getCity() + ", state=" + getState() + "]";
	}

	public ParsedLocation(String city, String state, String country) {
		setCity(city);
		setState(state);
		setCountry(country);
	}
}

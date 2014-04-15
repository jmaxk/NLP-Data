package max.nlp.dal.weka;

import java.util.Map;

import org.bson.types.ObjectId;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@SuppressWarnings("rawtypes")
public class ExtractedFeature extends BasicDBObject {

	
	private static final long serialVersionUID = 1L;
	public final static String extractedFeatureField = "extractedFeatures";

	public void setExtractedFeatures(Map extractedFeature) {
		Gson gson = new GsonBuilder().serializeSpecialFloatingPointValues().create();
		String json = gson.toJson(extractedFeature);
		put(extractedFeatureField, json);
	}

	public Map getExtractedFeatures() {
		Object val = get(extractedFeatureField);
		if (val == null)
			return null;
		else {
			String mapAsJson = (String) val;
			Gson gson = new Gson();
			Map feats = gson.fromJson(mapAsJson, Map.class);
			return feats;
		}
	}

	public final static String featureExtractorNameField = "featureExtractorName";

	public void setFeatureExtractor(String featureExtractorName) {

		put(featureExtractorNameField, featureExtractorName);
	}

	public String getFeatureExtractorName() {
		Object val = get(featureExtractorNameField);
		if (val == null)
			return null;
		else
			return (String) val;
	}

	public final static String extractionSourceField = "extractionSource";

	public void setExtractionSource(ObjectId extractionSource) {
		put(extractionSourceField, extractionSource);
	}

	public ObjectId getExtractionSource() {
		Object val = get(extractionSourceField);
		if (val == null)
			return null;
		else
			return (ObjectId) val;
	}

	public ExtractedFeature(Map extractedFeatures, String featureExtractorName,
			ObjectId extractionSource) {
		setExtractedFeatures(extractedFeatures);
		setFeatureExtractor(featureExtractorName);
		setExtractionSource(extractionSource);
	}

	public ExtractedFeature(Map next) {
		super(next);
	}

	public ExtractedFeature(DBObject dbo) {
		super(dbo.toMap());
	}

	public ExtractedFeature() {
	}

}

package max.nlp.dal.blog.blogger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class BlogAuthorProfile extends BasicDBObject {

	public BlogAuthorProfile(String profileURL) {
		setUrl(profileURL);
	}

	private static final long serialVersionUID = 1L;

	public ObjectId getId() {
		return (ObjectId) get("_id");
	}

	public final static String urlField = "url";

	public String getUrl() {
		return (String) get(urlField);
	}

	public void setUrl(String url) {
		put(urlField, url);
	}

	// blog
	public String blogsfield = "blogs";

	public List<String> getBlogs() {
		@SuppressWarnings("unchecked")
		List<String> blogz = (List<String>) get(this.blogsfield);
		if (blogz == null)
			return new ArrayList<String>();
		else
			return blogz;
	}

	public void setBlogs(List<String> blogs) {
		put(blogsfield, blogs);
	}

	public void addBlog(String blog) {
		List<String> blogz = getBlogs();
		if (blogz == null) {
			blogz = new ArrayList<String>();
		}
		blogz.add(blog);
		setBlogs(blogz);
	}

	// category info
	public final static String categoryInfoField= "categoryInfo";

	@SuppressWarnings("unchecked")
	public List<BasicDBObject> getCategoryInfo() {
		List<BasicDBObject> catInfo = (List<BasicDBObject>) get(categoryInfoField);
		if (catInfo == null) {
			return new ArrayList<BasicDBObject>();
		} else
			return catInfo;
	}

	public void setCategoryInfo(List<BasicDBObject> categoryInfo) {
		put(categoryInfoField, categoryInfo);
	}

	public void addMetadata(String cat, String field, String value) {
		List<BasicDBObject> categoryInfo = getCategoryInfo();
		BasicDBObject catInfo = new BasicDBObject();
		catInfo.put("category", cat);
		catInfo.put("field", field);
		catInfo.put("value", value);

		categoryInfo.add(catInfo);
		setCategoryInfo(categoryInfo);
	}

	
	public final static String parsedLocationField = "parsedLocation";

	public void setParsedLocation(BasicDBObject parsedLocation) {
		put(parsedLocationField, parsedLocation);
	}

	public ParsedLocation getParsedLocation() {
		Object pl = get(parsedLocationField);
		if (pl != null)
			return new ParsedLocation((DBObject) get(parsedLocationField));
		else
			return null;
	}
	
	public final static String parsedGenderField = "parsedGender";

	public void setparsedGender(String parsedGender) {
		put(parsedGenderField, parsedGender);
	}

	public String getParsedGenderField() {
		Object val = get(parsedGenderField);
		if (val == null)
			return null;
		else
			return (String) val;
	}
	

	public BlogAuthorProfile(@SuppressWarnings("rawtypes") Map next) {
		super(next);
	}
	
	public BlogAuthorProfile(DBObject dbo) {
		super(dbo.toMap());
	}

}

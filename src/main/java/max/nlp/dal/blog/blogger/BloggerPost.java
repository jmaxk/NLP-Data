package max.nlp.dal.blog.blogger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import max.nlp.dal.annotations.DBpediaAnnotation;

import org.bson.types.ObjectId;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.blogger.model.Post;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class BloggerPost extends BasicDBObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final JacksonFactory jsonFactory = new JacksonFactory();

	private String post = "post";
	private String cleanContent = "cleanContent";

	public ObjectId getId() {
		return (ObjectId) get("_id");
	}

	public void setCleanContent(String cleantxt) {
		put(cleanContent, cleantxt);
	}

	public String getContent() {
		return getOriginalPost().getContent();
	}

	public final static String titleField = "title";

	public void setTitle(String title) {
		put(titleField, title);
	}

	public String getTitle() {
		Object val = get(titleField);
		if (val == null)
			return null;
		else
			return (String) val;
	}

	public String getCleanContent() {
		Object cc = get(cleanContent);
		if (cc == null)
			return "";
		else
			return (String) cc;
	}

	public void fromBloggerPost(Post p) {
		Object o = com.mongodb.util.JSON.parse(p.toString());
		BasicDBObject postAsDBObject = (BasicDBObject) o;
		put(post, postAsDBObject);
	}

	public Post getOriginalPost() {
		try {
			// this will need to change, but it requiers a new db....because now
			// the object is post, but it need sa field post
			// BasicDBObject postAsDBObject = (BasicDBObject) get(post);
			Post usablePost = jsonFactory.fromString(this.toString(),
					Post.class);
			return usablePost;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public final static String peopleField = "people";

	public void setpeople(List<String> people) {
		put(peopleField, people);
	}

	public List<String> getPeople() {
		BasicDBList items = (BasicDBList) get(peopleField);

		List<String> usableItems = new ArrayList<String>();
		if (items == null || items.isEmpty())
			return usableItems;
		else {
			for (Object dbo : items) {
				usableItems.add((String) dbo);
			}
			return usableItems;
		}

	}

	public final static String organizationField = "organization";

	public void setorganization(List<String> organization) {
		put(organizationField, organization);
	}

	public List<String> getOrganization() {
		BasicDBList items = (BasicDBList) get(organizationField);

		List<String> usableItems = new ArrayList<String>();
		if (items == null || items.isEmpty())
			return usableItems;
		else {
			for (Object dbo : items) {
				usableItems.add((String) dbo);
			}
			return usableItems;
		}

	}

	public final static String locationsField = "locations";

	public void setlocations(List<String> locations) {
		put(locationsField, locations);
	}

	public List<String> getLocations() {
		BasicDBList items = (BasicDBList) get(locationsField);

		List<String> usableItems = new ArrayList<String>();
		if (items == null || items.isEmpty())
			return usableItems;
		else {
			for (Object dbo : items) {
				usableItems.add((String) dbo);
			}
			return usableItems;
		}

	}

	public BloggerPost(DBObject dbo) {
		super(dbo.toMap());
	}

	public BloggerPost() {
	}

	public static List<BloggerPost> fromList(List<BasicDBObject> googlePosts) {
		ArrayList<BloggerPost> posts = new ArrayList<BloggerPost>();
		for (BasicDBObject gp : googlePosts) {
			posts.add(new BloggerPost(gp));
		}
		return posts;
	}

	public static List<BloggerPost> fromRSS(Elements entries) {
		ArrayList<BloggerPost> posts = new ArrayList<BloggerPost>();
		for (int i = 0; i < entries.size(); i++) {
			BloggerPost p = new BloggerPost();
			Element entry = entries.get(i);
			Elements text = entry.select("content");
			if (text.size() > 0) {
				if (text.get(0) != null) {
					p.setCleanContent(Jsoup.parse(text.get(0).text()).text());
				}
				posts.add(p);
			}
		}
		return posts;
	}

	public final static String dbpediaAnnotationField = "dbpediaAnnotation";

	public void setdbpediaAnnotation(List<DBpediaAnnotation> dbpediaAnnotation) {
		put(dbpediaAnnotationField, dbpediaAnnotation);
	}

	public List<DBpediaAnnotation> getDBpediaAnnotation() {
		BasicDBList items = (BasicDBList) get(dbpediaAnnotationField);

		List<DBpediaAnnotation> usableItems = new ArrayList<DBpediaAnnotation>();
		if (items == null || items.isEmpty())
			return usableItems;
		else {
			for (Object dbo : items) {
				usableItems.add(new DBpediaAnnotation((DBObject) dbo));
			}
			return usableItems;
		}

	}
}

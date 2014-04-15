package max.nlp.dal.blog.blogger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class BloggerBlog extends BasicDBObject {

	public ObjectId getId() {
		return (ObjectId) get("_id");
	}

	public final static String sourceField = "source";

	public void setSource(String source) {
		put(sourceField, source);
	}

	public String getSource() {
		Object val = get(sourceField);
		if (val == null)
			return null;
		else
			return (String) val;
	}

	public String getAllText() {
		StringBuilder sb = new StringBuilder();
		for (BloggerPost p : getPosts()) {
			sb.append(p.getCleanContent());
		}
		return sb.toString();
	}

	private String url = "url"; // string

	public void setUrl(String burl) {
		put(this.url, burl);
	}

	public String getUrl() {
		return (String) get(this.url);
	}

	private String authorID = "authorID"; // ObjectId

	public void setAuthorID(ObjectId authID) {
		put(this.authorID, authID);
	}

	public ObjectId getAuthorID() {
		return (ObjectId) get(this.authorID);
	}

	private String cleanText = "cleanText"; // String

	public void setCleanText(String cleanedText) {
		put(this.cleanText, cleanedText);
	}

	public String getCleanText() {
		return (String) get(this.cleanText);
	}

	private String name = "name"; // string

	public void setName(String name) {
		put(this.name, name);
	}

	public String getName() {
		return (String) get(this.name);
	}

	private String posts = "posts"; // List<BloggerPost>

	public void setPosts(List<BloggerPost> posts) {
		put(this.posts, posts);
	}

	public List<BloggerPost> getPosts() {
		BasicDBList postsFromDB = (BasicDBList) get(posts);

		List<BloggerPost> usablePosts = new ArrayList<BloggerPost>();
		if (postsFromDB == null || postsFromDB.isEmpty())
			return usablePosts;
		for (Object dbo : postsFromDB) {
			BloggerPost p = new BloggerPost((DBObject) dbo);
			usablePosts.add(p);
		}
		return usablePosts;

	}

	private String blogID = "blogID";

	public void setBlogId(String blogID) {
		put(this.blogID, blogID);
	}

	public String getblogID() {
		return (String) get(this.blogID);
	}

	public ObjectId getAuthor() {
		return (ObjectId) get(authorID);
	}

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("rawtypes")
	public BloggerBlog(Map next) {
		super(next);
	}

	public BloggerBlog(DBObject dbo) {
		super(dbo.toMap());
	}

	public BloggerBlog() {
	}

}

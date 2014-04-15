package max.nlp.dal.blog.blogger;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import max.nlp.dal.weka.ExtractedFeature;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;

public class BloggerDB {

	private static BloggerDB self;
	private DB db;
	private DBCollection profiles;
	private DBCollection blogs;
	private DBCollection features;

	private BloggerDB() {
		try {
			MongoClient mongoClient = new MongoClient("localhost", 27017);
			db = mongoClient.getDB("blogz");
			profiles = db.getCollection("profiles");
			blogs = db.getCollection("blogs");
			features = db.getCollection("features");
			ensureIndeces();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void ensureIndeces() {
		features.ensureIndex(new BasicDBObject("featureExtractorName", 1));
		features.ensureIndex(new BasicDBObject("extractionSource", 1));
		profiles.ensureIndex(new BasicDBObject("extractionSource", 1));

	}

	public WriteResult updatePosts(DBObject updateQuery, DBObject updateCommand) {

		try {
			WriteResult result = blogs.update(updateQuery, updateCommand, true,
					true);
			return result;

		} catch (Exception e) {
			// should log this at some point
		}
		return null;
	}

	public void removeBlog(DBObject blog) {
		blogs.remove(blog);
	}

	public List<BasicDBObject> findBlogsForProfile(ObjectId profileId) {
		BasicDBObject query = new BasicDBObject();
		query.put("authorID", profileId);
		DBCursor cursor = blogs.find(query);
		List<BasicDBObject> matchingBlogs = new ArrayList<BasicDBObject>();
		while (cursor.hasNext()) {
			BasicDBObject blog = (BasicDBObject) cursor.next();
			matchingBlogs.add(blog);
		}
		return matchingBlogs;
	}

	public static void main(String[] args) {
		BloggerDB.getInstance();

		// try {
		// MongoClient mongoClient = new MongoClient("localhost", 27017);
		// DB adb = mongoClient.getDB("wiktionary");
		// DBCollection collection = adb.getCollection("WiktionaryEntry");
		// BasicDBObject db2 = new BasicDBObject();
		// db2.put("className", "ingest.wiktionary.types.WiktionaryEntry");
		// DBCursor entries = collection.find(db2);
		// Iterator<DBObject> iterator = entries.iterator();
		// int i = 0;
		// while (iterator.hasNext()){
		// DBObject dbo = iterator.next();
		//
		// collection.update(dbo, new BasicDBObject( "$set", new
		// BasicDBObject("className",
		// "max.nlp.dal.wiktionary.types.WiktionaryEntry")));
		// if (++i % 1000 ==0)
		// System.out.println(i);
		// }
		// } catch (UnknownHostException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	public DBCursor getProfileIterator() {
		DBCursor cursor = profiles.find();
		return cursor;
	}

	public BlogAuthorProfile findProfileForBlog(final BloggerBlog b) {
		@SuppressWarnings("serial")
		ArrayList<String> urlAsStr = new ArrayList<String>() {
			{
				{
					add(b.getUrl());
				}
			}
		};

		BasicDBObject query = new BasicDBObject("blogs", new BasicDBObject(
				"$in", urlAsStr));
		BlogAuthorProfile p = new BlogAuthorProfile(
				((DBObject) profiles.findOne(query)));
		return p;
	}

	public BlogAuthorProfile findProfileForBlog(final String url) {
		@SuppressWarnings("serial")
		ArrayList<String> urlAsStr = new ArrayList<String>() {
			{
				{
					add(url);
				}
			}
		};

		BasicDBObject query = new BasicDBObject("blogs", new BasicDBObject(
				"$in", urlAsStr));
		BlogAuthorProfile p = new BlogAuthorProfile(
				((DBObject) profiles.findOne(query)));
		return p;
	}

	public DBCursor getBlogIterator() {
		DBCursor cursor = blogs.find();
		return cursor;

	}

	public static BloggerDB getInstance() {
		if (self == null) {
			self = new BloggerDB();
		}
		return self;
	}

	public void saveProfile(BlogAuthorProfile p) {
		profiles.save(p);
	}

	public BlogAuthorProfile findProfileByURL(String url) {
		BasicDBObject query = new BasicDBObject();
		query.put("url", url);
		DBCursor cursor = profiles.find(query);
		while (cursor.hasNext())
			return new BlogAuthorProfile(cursor.next().toMap());
		return null;
	}

	public BloggerBlog findBlogbyURL(String url) {
		BasicDBObject query = new BasicDBObject();
		query.put("url", url);
		DBCursor cursor = blogs.find(query);
		while (cursor.hasNext())
			return new BloggerBlog(cursor.next().toMap());
		return null;
	}

	public void saveBlog(BloggerBlog b) {
		try {
			blogs.save(b);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public BloggerBlog findBlogByBloggerId(String id) {
		BasicDBObject query = new BasicDBObject();
		query.put("blogID", id);
		DBObject dbObj = blogs.findOne(query);
		return new BloggerBlog(dbObj.toMap());
	}

	public DBObject findProfilesById(String id) {
		BasicDBObject query = new BasicDBObject();
		query.put("_id", new ObjectId(id));
		DBObject dbObj = blogs.findOne(query);
		return dbObj;
	}

	public DBCursor findBlog(DBObject query) {
		DBCursor res = blogs.find(query);
		return res;
	}

	public void updateBlog(DBObject oldObj, DBObject newObj) {

		blogs.update(oldObj, newObj);
	}

	public void updateProfile(DBObject oldObj, DBObject newObj) {

		profiles.update(oldObj, newObj);
	}

	public List<BlogAuthorProfile> getProfilesForCity(String city) {
		List<BlogAuthorProfile> matchingProfiles = new ArrayList<BlogAuthorProfile>();
		BasicDBObject query = new BasicDBObject();
		query.put("parsedLocation.city", city);
		DBCursor itr = profiles.find(query);
		while (itr.hasNext()) {
			matchingProfiles.add(new BlogAuthorProfile(itr.next()));
		}
		return matchingProfiles;
	}

	public List<BlogAuthorProfile> getProfilesForState(String state) {
		List<BlogAuthorProfile> matchingProfiles = new ArrayList<BlogAuthorProfile>();
		BasicDBObject query = new BasicDBObject();
		query.put("parsedLocation.state", state);
		DBCursor itr = profiles.find(query);
		while (itr.hasNext()) {
			matchingProfiles.add(new BlogAuthorProfile(itr.next()));
		}
		return matchingProfiles;
	}

	public List<BlogAuthorProfile> getProfilesWithKV(String key, String value) {
		List<BlogAuthorProfile> matchingProfiles = new ArrayList<BlogAuthorProfile>();
		BasicDBObject query = new BasicDBObject();
		query.put(key, value);
		DBCursor itr = profiles.find(query);
		while (itr.hasNext()) {
			matchingProfiles.add(new BlogAuthorProfile(itr.next()));
		}
		return matchingProfiles;
	}

	public WriteResult updatePost(BloggerPost p, String field, List newval,
			BloggerBlog b) {
		BasicDBObject query = new BasicDBObject();
		query.append("blogID", b.getblogID());
		query.append("posts.id", p.getOriginalPost().getId());
		// "posts.$.locations"
		BasicDBObject update = new BasicDBObject();
		update.append(field, newval);
		BasicDBObject updateCommand = new BasicDBObject("$set", update);
		WriteResult res = blogs.update(query, updateCommand);
		return res;
	}

	public BloggerBlog findBlogWithPost(BloggerPost p, BloggerBlog b) {
		BasicDBObject query = new BasicDBObject();
		query.append("blogID", b.getblogID());
		query.append("posts.id", p.getOriginalPost().getId());
		return new BloggerBlog(blogs.findOne(query));
	}

	public BloggerBlog findBlogWithPost(String pid, String bid) {
		BasicDBObject query = new BasicDBObject();
		query.append("blogID", bid);
		query.append("posts.id", pid);
		return new BloggerBlog(blogs.findOne(query));
	}

	public void saveExtractedFeature(ExtractedFeature f) {
		features.save(f);
	}

	public ExtractedFeature findExtractedFeature(BloggerBlog b,
			String usedFeatureExtractorName) {
		BasicDBObject query = new BasicDBObject();
		query.put(ExtractedFeature.extractionSourceField, b.getId());
		query.put(ExtractedFeature.featureExtractorNameField,
				usedFeatureExtractorName);
		DBObject dbObj = features.findOne(query);
		if (dbObj != null)
			return new ExtractedFeature(dbObj.toMap());
		else
			return null;
	}

	public ExtractedFeature findExtractedFeature(BloggerPost p,
			String usedFeatureExtractorName) {
		BasicDBObject query = new BasicDBObject();
		query.put(ExtractedFeature.extractionSourceField, p.getOriginalPost()
				.getId());
		query.put(ExtractedFeature.featureExtractorNameField,
				usedFeatureExtractorName);
		DBObject dbObj = features.findOne(query);
		if (dbObj != null)
			return new ExtractedFeature(dbObj.toMap());
		else
			return null;
	}

	public void cleanExtractedFeautres() {
		DBCursor cursor = features.find();
		ArrayList<ExtractedFeature> done = new ArrayList<ExtractedFeature>();
		while (cursor.hasNext()) {
			ExtractedFeature f = new ExtractedFeature(cursor.next());
			if (f.getFeatureExtractorName().contains("liwc"))
				done.add(f);
		}
		for (ExtractedFeature o : done) {
			features.remove(o);

		}
	}

}

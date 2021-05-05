package com.mytoolbase.JavaBE.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Companies {
	@Id
	private String _id;
	private String name;
	private String permalink;
	private String crunchbaseUrl;
	private String homepageUrl;
	private String blogUrl;
	private String blogFeedUrl;
	private String twitterUsername;
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPermalink() {
		return permalink;
	}
	public void setPermalink(String permalink) {
		this.permalink = permalink;
	}
	public String getCrunchbaseUrl() {
		return crunchbaseUrl;
	}
	public void setCrunchbaseUrl(String crunchbaseUrl) {
		this.crunchbaseUrl = crunchbaseUrl;
	}
	public String getHomepageUrl() {
		return homepageUrl;
	}
	public void setHomepageUrl(String homepageUrl) {
		this.homepageUrl = homepageUrl;
	}
	public String getBlogUrl() {
		return blogUrl;
	}
	public void setBlogUrl(String blogUrl) {
		this.blogUrl = blogUrl;
	}
	public String getBlogFeedUrl() {
		return blogFeedUrl;
	}
	public void setBlogFeedUrl(String blogFeedUrl) {
		this.blogFeedUrl = blogFeedUrl;
	}
	public String getTwitterUsername() {
		return twitterUsername;
	}
	public void setTwitterUsername(String twitterUsername) {
		this.twitterUsername = twitterUsername;
	}
}

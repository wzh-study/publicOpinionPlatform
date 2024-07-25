package com.example.publicopinion.entity;

import lombok.Data;

@Data
public class Post {
	private String date;
	private String headline;
	private String url;
	private String openingText;
	private String hitSentence;
	private String platform;
	private String influencer;
	private String country;
	private String language;
	private String sentiment;
	private String keyPhrases;
	private String inputName;
	private String keywords;
	private String tweetId;
	private String twitterId;
	private Integer twitterFollowers;
	private Integer twitterFollowing;
	private String postId;
	private String postUsername;
	private String postedId;
	private String postedUsername;
	// Getters and setters
}


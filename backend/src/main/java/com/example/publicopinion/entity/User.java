package com.example.publicopinion.entity;

import lombok.Data;

import java.math.BigInteger;

@Data

public class User {
	private BigInteger userId;
	private String username;
	private int followingCount;
	private int followedCount;
	private int postCount;
	private String location;
	// Getters and Setters
}

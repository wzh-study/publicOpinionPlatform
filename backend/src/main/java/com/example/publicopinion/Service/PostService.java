package com.example.publicopinion.Service;

import com.example.publicopinion.mapper.PostMapper;
import com.example.publicopinion.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class PostService {

	@Autowired
	private PostMapper postMapper;

	public List<Post> getAllPostInfo() {
		return postMapper.getAllPostInfo();
	}
	public List<Post> getPostInfoByUsername(String username) {
		return postMapper.getPostInfoByUsername(username);
	}
}

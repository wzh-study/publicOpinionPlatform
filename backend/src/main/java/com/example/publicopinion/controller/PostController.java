package com.example.publicopinion.controller;

import com.example.publicopinion.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.example.publicopinion.Service.PostService;
import com.example.publicopinion.entity.Post;

@RestController
@RequestMapping("/post")
@CrossOrigin(origins = "*", allowedHeaders = "*")  //  处理不同源

public class PostController {

	@Autowired
	private PostService postService;

	@GetMapping("/getAllPostInfo")
	public Map<String, Object> getAllPostInfo() {
		List<Post> posts = postService.getAllPostInfo();
		return createResponse(200, "success", posts);
	}

	@PostMapping("/getPostInfoByUsername")
	public Map<String, Object> getPostInfoByUsername(@RequestBody Map<String, String> request) {
		String username = request.get("username");
		if (username == null || username.length() < 1 || username.length() > 30) {
			return createErrorResponse(504, "notInfo");
		}
		List<Post> posts = postService.getPostInfoByUsername(username);
		return createResponse(200, "success", posts);
	}

	private Map<String, Object> createResponse(int code, String message, List<Post> data) {
		Map<String, Object> response = new HashMap<>();
		response.put("code", code);
		response.put("message", message);
		response.put("data", data);
		// 假设页码信息和大小为固定值；实际情况下，应该是动态的。
		response.put("pageNum", 1);
		response.put("pageSize", 10);
		response.put("totalPage", data.size() / 10 + 1);
		response.put("totalSize", data.size());
		return response;
	}

	private Map<String, Object> createErrorResponse(int code, String message) {
		Map<String, Object> response = new HashMap<>();
		response.put("code", code);
		response.put("message", message);
		response.put("data", null);
		return response;
	}
}

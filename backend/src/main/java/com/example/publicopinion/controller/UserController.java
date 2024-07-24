package com.example.publicopinion.controller;

import com.example.publicopinion.entity.User;
import com.example.publicopinion.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")  //  处理不同源
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping("/getAllBaseUserInfo")
	public Map<String, Object> getAllBaseUserInfo() {
		List<User> users = userService.getAllBaseUserInfo();

		return createResponse(200, "success", users);
	}

	@PostMapping("/getBaseUserInfo")
	public Map<String, Object> getBaseUserInfo(@RequestBody User user) {
		String username = user.getUsername();
		List<User> users = userService.getBaseUserInfoByUsername(username);
		return createResponse(200, "success", users);
	}

	private Map<String, Object> createResponse(int code, String message, List<User> data) {
		Map<String, Object> response = new HashMap<>();
		response.put("code", code);
		response.put("message", message);
		response.put("data", data);
		// Assuming page info is hardcoded for simplicity; in real cases, this would be dynamic.
		response.put("pageNum", 1);
		response.put("pageSize", 10);
		response.put("totalPage", data.size() / 10 + 1); // For example
		response.put("totalSize", data.size());
		return response;
	}
}



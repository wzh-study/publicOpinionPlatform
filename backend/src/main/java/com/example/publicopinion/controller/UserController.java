package com.example.publicopinion.controller;

import com.example.publicopinion.entity.User;
import com.example.publicopinion.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping("/getBaseUserInfo")
	public Map<String, Object> getBaseUserInfo(@RequestParam String username) {
		Map<String, Object> response = new HashMap<>();
		List<User> users = userService.getUsersByUsername(username);

		if (users != null) {
			response.put("code", 200);
			response.put("message", "success");
			response.put("data", users);
			// Assuming we have pagination details, otherwise this part needs to be implemented
			response.put("pageNum", 1);
			response.put("pageSize", users.size());
			response.put("totalPage", 1);
			response.put("totalSize", users.size());
		} else {
			response.put("code", 504);
			response.put("message", "notLogin");
			response.put("data", null);
		}

		return response;
	}
}

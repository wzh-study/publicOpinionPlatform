package com.example.publicopinion.Service;

import com.example.publicopinion.mapper.UserMapper;
import com.example.publicopinion.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;

	public List<User> getUsersByUsername(String username) {
		return userMapper.getUsersByUsername(username);
	}

	public List<User> getAllBaseUserInfo() {
		return userMapper.getAllBaseUserInfo();
	}

	public List<User> getBaseUserInfoByUsername(String username) {
		return userMapper.getBaseUserInfoByUsername(username);
	}
}







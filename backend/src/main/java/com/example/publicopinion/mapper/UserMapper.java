package com.example.publicopinion.mapper;

import com.example.publicopinion.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
	@Select("SELECT * FROM user_info WHERE username LIKE CONCAT('%', #{username}, '%')")
	List<User> getUsersByUsername(String username);
}
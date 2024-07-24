package com.example.publicopinion.mapper;

import com.example.publicopinion.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;



import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface UserMapper {
	@Select("SELECT * FROM user_social_account WHERE username LIKE CONCAT('%', #{username}, '%')")
	List<User> getUsersByUsername(String username);

	@Select("SELECT * FROM user_social_account")
	List<User> getAllBaseUserInfo();

	@Select("SELECT * FROM user_social_account WHERE username LIKE CONCAT('%', #{username}, '%')")
	List<User> getBaseUserInfoByUsername(@Param("username") String username);
}





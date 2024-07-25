package com.example.publicopinion.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.example.publicopinion.entity.Post;
import java.util.List;

@Mapper

public interface PostMapper {

	@Select("SELECT * FROM post")
	List<Post> getAllPostInfo();
	@Select("SELECT * FROM post WHERE post_username = #{username}")
	List<Post> getPostInfoByUsername(@Param("username") String username);
}

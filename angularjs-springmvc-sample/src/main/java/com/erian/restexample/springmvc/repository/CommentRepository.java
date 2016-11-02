package com.erian.restexample.springmvc.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.erian.restexample.springmvc.domain.Comment;
import com.erian.restexample.springmvc.domain.Post;


public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	public List<Comment> findByPost(Post post);

    public Page<Comment> findByPostId(Long id, Pageable page);
	
}

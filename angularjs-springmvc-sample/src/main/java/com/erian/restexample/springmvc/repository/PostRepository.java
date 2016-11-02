package com.erian.restexample.springmvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.erian.restexample.springmvc.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long>,//
        JpaSpecificationExecutor<Post>{

}

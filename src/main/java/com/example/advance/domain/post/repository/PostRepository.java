package com.example.advance.domain.post.repository;

import com.example.advance.common.entity.Post;
import com.example.advance.domain.post.model.dto.PostSummaryDto;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    //게시글들을 가져옴 -> 게시글에 엮여 있는 지연로딩으로 되어 있는 comment를 즉시 로딩으로 가져올 것
    @Query("""
            SELECT new com.example.advance.domain.post.model.dto.PostSummaryDto(
            p.content, SIZE(p.comments) ) 
            FROM Post p
            WHERE p.user.username = :username""")
    List<PostSummaryDto> findAllWithCommentsByUsername(@Param("username") String username);

    @EntityGraph(attributePaths = {"user","comments"})
    List<Post> findByUserUsername(String username);
}

package com.example.advance.domain.comment.service;

import com.example.advance.common.entity.Comment;
import com.example.advance.common.entity.Post;
import com.example.advance.domain.comment.model.dto.CommentDto;
import com.example.advance.domain.comment.repository.CommentRepository;
import com.example.advance.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentDto createComment(Long postId, String content) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("등록된 Post가 없습니다.")
        );

        Comment comment = new Comment(content, post);

        Comment saved = commentRepository.save(comment);

        return CommentDto.from(saved);
    }
}

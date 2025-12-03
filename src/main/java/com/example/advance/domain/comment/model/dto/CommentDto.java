package com.example.advance.domain.comment.model.dto;

import com.example.advance.common.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Long commentId;
    private String content;
    private Long postId;


    public static CommentDto from (Comment comment) {
        return new CommentDto(comment.getId(), comment.getContent(), comment.getPost().getId());
    }
}

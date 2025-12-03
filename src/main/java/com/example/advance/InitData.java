package com.example.advance;

import com.example.advance.common.entity.Comment;
import com.example.advance.common.entity.Post;
import com.example.advance.common.entity.User;
import com.example.advance.common.enums.UserRoleEnum;
import com.example.advance.domain.comment.repository.CommentRepository;
import com.example.advance.domain.post.repository.PostRepository;
import com.example.advance.domain.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
public class InitData {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;


    @PostConstruct
    @Transactional
    public void init() {

        User user1 = new User("이서준", passwordEncoder.encode("1234"), "lee@seo.jun", UserRoleEnum.ADMIN);
        User user2 = new User("이준범", passwordEncoder.encode("1234"), "lee@jun.beum", UserRoleEnum.NORMAL);

        userRepository.save(user1);
        userRepository.save(user2);

        Post post1 = new Post("1번 게시글", user1);
        Post post2 = new Post("2번 게시글", user1);
        Post post3 = new Post("3번 게시글", user2);
        Post post4 = new Post("4번 게시글", user2);

        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);
        postRepository.save(post4);

        Comment comment1 = new Comment("댓글 1번", post1);
        Comment comment2 = new Comment("댓글 2번", post1);
        Comment comment3 = new Comment("댓글 3번", post2);
        Comment comment4 = new Comment("댓글 4번", post2);
        Comment comment5 = new Comment("댓글 5번", post3);
        Comment comment6 = new Comment("댓글 6번", post3);
        Comment comment7 = new Comment("댓글 7번", post3);
        Comment comment8 = new Comment("댓글 8번", post4);
        Comment comment9 = new Comment("댓글 9번", post4);

        commentRepository.save(comment1);
        commentRepository.save(comment2);
        commentRepository.save(comment3);
        commentRepository.save(comment4);
        commentRepository.save(comment5);
        commentRepository.save(comment6);
        commentRepository.save(comment7);
        commentRepository.save(comment8);
        commentRepository.save(comment9);


    }
}

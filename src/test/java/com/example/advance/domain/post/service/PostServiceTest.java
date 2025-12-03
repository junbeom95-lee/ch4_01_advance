package com.example.advance.domain.post.service;

import com.example.advance.common.entity.Post;
import com.example.advance.common.entity.User;
import com.example.advance.common.enums.UserRoleEnum;
import com.example.advance.data.PostFixture;
import com.example.advance.data.UserFixture;
import com.example.advance.domain.post.model.dto.PostDto;
import com.example.advance.domain.post.repository.PostRepository;
import com.example.advance.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) //PostService 테스트 할 때 Mock을 선언할 거야
class PostServiceTest {

    @Mock   //가짜 객체
    private PostRepository postRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PostService postService;

    private User testUser;

    //사용하지 않은 곳은 불필요한 세팅이 되므로 생각해볼 필요가 있음
    @BeforeEach
    void setUp() {
        User testUser = UserFixture.createUserAdminRole();

    }

    //게시글 생성 단위 테스트 진행
    @Test
    @DisplayName("게시글 생성 성공 - 유효한 사용자와 내용이 주어졌을 때 - 성공 케이스")
    void createPost_success() {
        //given
        Post testPost = PostFixture.createPost1();

        when(userRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));
        when(postRepository.save(any(Post.class))).thenReturn(testPost);

        //when
        PostDto result = postService.createPost(testUser.getUsername(), testPost.getContent());

        //then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEqualTo("테스트 게시글 입니다");
        assertThat(result.getId()).isEqualTo(testPost.getId());
        assertThat(result.getUsername()).isEqualTo("이서준");

        //실행이 되었는지 안되었는지 몇 번 호출했는지 확인하는 메서드
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    @DisplayName("사용자 명으로 게시글 목록 조회 - 성공 케이스")
    void getPostListByUsername_success() {
        //given
        User testUser = UserFixture.createUserAdminRole();
        String username = testUser.getUsername();

        List<Post> postList = List.of(
                PostFixture.createPost1(),
                PostFixture.createPost2());

        testUser.getPosts().addAll(postList);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(testUser));    //find 메서드를 했을 때 testUser를 반환할 것

        //when
        List<PostDto> result = postService.getPostListByUsername(username);

        //then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getUsername()).isEqualTo(username);
        assertThat(result.get(0).getContent()).isEqualTo("테스트 게시글1");
        assertThat(result.get(0).getContent()).isEqualTo("테스트 게시글2");

    }

    @Test
    @DisplayName("사용자 명으로 게시글 목록 조회")
    void getPostListByUsername_fail_case() {
        //given
        when(userRepository.findByUsername("이서준")).thenReturn(Optional.empty());

        //when
        assertThatThrownBy(() -> postService.getPostListByUsername("이서준"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("등록된 사용자가 없습니다.");    //메시지까지 일치하는지 확인해줌

        //then
    }

}
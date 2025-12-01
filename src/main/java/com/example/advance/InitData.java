package com.example.advance;

import com.example.advance.common.entity.User;
import com.example.advance.common.enums.UserRoleEnum;
import com.example.advance.user.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InitData {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @PostConstruct
    @Transactional
    public void init() {
        List<User> userList =
                List.of(
                        new User("이서준", passwordEncoder.encode("1234"), "lee@seo.jun", UserRoleEnum.ADMIN),
                        new User("이준범", passwordEncoder.encode("1234"), "lee@jun.beum", UserRoleEnum.NORMAL)

                );

        for(User user : userList) {
            userService.save(user);
        }
    }


}

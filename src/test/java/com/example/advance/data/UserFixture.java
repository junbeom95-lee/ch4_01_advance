package com.example.advance.data;

import com.example.advance.common.entity.User;
import com.example.advance.common.enums.UserRoleEnum;

public class UserFixture {

    public static String DEFAULT_USERNAME = "이서준";
    public static String DEFAULT_PASSWORD = "1234";
    public static String DEFAULT_EMAIL = "lee@seo.jun";

    public static User createUserAdminRole() {
        return new User(DEFAULT_USERNAME, DEFAULT_PASSWORD, DEFAULT_EMAIL, UserRoleEnum.ADMIN);
    }

    public static User createUserNormalRole() {
        return new User(DEFAULT_USERNAME, DEFAULT_PASSWORD, DEFAULT_EMAIL, UserRoleEnum.NORMAL);
    }

}

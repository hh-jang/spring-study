package com.hhjang.spring.study.transaction.repository;

import com.hhjang.spring.study.transaction.user.User;
import com.hhjang.spring.study.transaction.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void createUser() {
        User user = new User();
        String email = "test@gmail.com";
        String name = "tester";

        user.setEmail(email);
        user.setName(name);

        User user1 = userService.createUser(user);
        assertThat(user1.getEmail()).isEqualTo(email);
        assertThat(user1.getName()).isEqualTo(name);
    }
}

package com.hhjang.spring.study.transaction.repository;

import com.hhjang.spring.study.transaction.user.User;
import com.hhjang.spring.study.transaction.user.UserRepository;
import com.hhjang.spring.study.transaction.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("User를 생성하는 테스트")
    public void createUserTest() {
        User user = new User();
        String email = "test@gmail.com";
        String name = "tester";

        user.setEmail(email);
        user.setName(name);

        User user1 = userService.createUser(user);
        assertThat(user1.getEmail()).isEqualTo(email);
        assertThat(user1.getName()).isEqualTo(name);
    }

    @Test
    @DisplayName("User를 생성 후 Update하는 테스트")
    public void updateUserTest() {
        // Given
        User user = new User();
        String beforeEmail = "before_test@gmail.com";
        String beforeName = "before_tester";

        String afterEmail = "after_test@gmail.com";
        String afterName = "after_tester";

        user.setEmail(beforeEmail);
        user.setName(beforeName);

        // When
        User savedUser = userService.createUser(user);
        User updatedUser = userService.updateUser(savedUser.getId(), afterEmail, afterName);

        // Then
        assertThat(updatedUser.getId()).isEqualTo(savedUser.getId());
        assertThat(updatedUser.getEmail()).isEqualTo(afterEmail);
        assertThat(updatedUser.getName()).isEqualTo(afterName);
    }
}

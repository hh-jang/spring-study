package com.hhjang.spring.study.transaction;

import com.hhjang.spring.study.transaction.user.User;
import com.hhjang.spring.study.transaction.user.UserRepository;
import com.hhjang.spring.study.transaction.user.UserService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
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
public class IsolationUserServiceTest {

    @Autowired
    private IsolationUserService isolationUserService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private User defaultUser;

    @BeforeEach
    public void setUp() {
        createTestUser();
    }

    @AfterEach
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Non Repeatable Read를 재현하는 테스트 코드")
    public void nonRepeatableReadTest() {
        boolean result = isolationUserService.nonRepeatableRead(defaultUser);

        // dirty read가 발생했으면 true
        // TODO 짜놓고 보니 jpa persistance context가 관리해서 이렇게하면 재현 안됨! 수정 필요
        assertThat(result).isTrue();
    }

    private void createTestUser() {
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setName("tester");

        defaultUser = userService.createUser(user);
    }
}

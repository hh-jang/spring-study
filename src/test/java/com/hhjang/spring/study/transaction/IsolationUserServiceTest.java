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
        defaultUser = createTestUser();
    }

    @AfterEach
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Non Repeatable Read를 재현하는 테스트 코드")
    public void nonRepeatableReadTest() {
//        boolean occurNonRepeatableRead = isolationUserService.nonRepeatableRead(defaultUser);

//        assertThat(occurNonRepeatableRead).isTrue();
    }

    @Test
    @DisplayName("Phantom Read를 재현하는 테스트 코드")
    public void phantomReadTest() {
        User createNewUser = new User();
        createNewUser.setEmail("test@gmail.com");
        createNewUser.setName("tester");
        boolean occurNonRepeatableRead = isolationUserService.phantomRead(createNewUser);

        assertThat(occurNonRepeatableRead).isTrue();
    }

    private User createTestUser() {
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setName("tester");

        return userService.createUser(user);
    }
}

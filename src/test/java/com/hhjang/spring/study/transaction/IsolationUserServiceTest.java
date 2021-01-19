package com.hhjang.spring.study.transaction;

import com.hhjang.spring.study.transaction.user.User;
import com.hhjang.spring.study.transaction.user.UserRepository;
import com.hhjang.spring.study.transaction.user.UserService;
import org.junit.jupiter.api.AfterEach;
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

    @AfterEach
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("READ_COMMITTED에서 Non Repeatable Read를 재현하는 테스트 코드")
    public void nonRepeatableReadOccurInReadCommittedTest() throws InterruptedException {
        boolean occurNonRepeatableRead = isolationUserService.nonRepeatableRead_read_committed(createTestUser());

        assertThat(occurNonRepeatableRead).isTrue();
    }

    @Test
    @DisplayName("REPEATABLE_READ에서 Non Repeatable Read가 발생하지 않음을 재현하는 테스트 코드")
    public void nonRepeatableReadNotOccurInRepeatableReadTest() throws InterruptedException {
        boolean occurNonRepeatableRead = isolationUserService.nonRepeatableReadRepeatableRead(createTestUser());

        assertThat(occurNonRepeatableRead).isTrue();
    }

    @Test
    @DisplayName("REPEATABLE_READ에서 Phantom Read를 재현하는 테스트 코드")
    public void phantomReadOccurInRepeatableReadTest() {
        createTestUser();
        User generatedUser = generateTestUser();

        boolean occurNonRepeatableRead = isolationUserService.phantomReadRepeatableRead(generatedUser);

        assertThat(occurNonRepeatableRead).isTrue();
    }

    private User createTestUser() {
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setName("tester");

        return userService.createUser(user);
    }

    private User generateTestUser() {
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setName("tester");

        return user;
    }
}

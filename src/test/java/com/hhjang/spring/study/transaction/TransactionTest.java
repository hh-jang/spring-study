package com.hhjang.spring.study.transaction;

import com.hhjang.spring.study.transaction.user.User;
import com.hhjang.spring.study.transaction.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/*
 *  @Transactional 메소드를 직접 호출 시와 외부를 통해 호출 시 rollback을 확인하는 테스트
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TransactionTest {

    @Autowired
    private ProxyUserService proxyUserService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("내부에서 직접 호출 시, 프록시 객체에 의해 Exception이 발생해도 롤백되지 않음을 확인하는 테스트")
    public void transactionNotRollbackTest() {
        User tester = new User();
        tester.setEmail("test@gmail.com");
        tester.setName("tester");

        try {
            proxyUserService.createUserOuter(tester);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        List<User> allUser = userRepository.findAll();
        assertThat(allUser.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Spring AOP Proxy에 의해 외부에서 호출하여 롤백이 되는 것을 확인하는 테스트")
    public void transactionRollbackTest() {
        User tester = new User();
        tester.setEmail("test@gmail.com");
        tester.setName("tester");

        try {
            proxyUserService.createUserThrowException(tester);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        List<User> allUser = userRepository.findAll();
        assertThat(allUser.size()).isEqualTo(0);
    }
}

package com.hhjang.spring.study.transaction;

import com.hhjang.spring.study.transaction.user.User;
import com.hhjang.spring.study.transaction.user.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 *  트랜잭션 테스트를 위해 userService를 주입받음
 *  Transaction test를 위한 createUserThrowException @Transactional 추가
 */

@Service("proxyUserService")
public class ProxyUserService {

    private final UserService userService;

    public ProxyUserService(@Qualifier("userService") UserService userService) {
        this.userService = userService;
    }

    @Transactional
    public User createUserThrowException(User user) {
        this.userService.createUser(user);
        throw new RuntimeException("occur create user runtime exception!!!");
    }

    public User createUserOuter(User user) {
        return createUserThrowException(user);
    }
}

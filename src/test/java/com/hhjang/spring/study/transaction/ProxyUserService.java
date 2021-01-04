package com.hhjang.spring.study.transaction;

import com.hhjang.spring.study.transaction.user.User;
import com.hhjang.spring.study.transaction.user.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 *  UserService proxy 객체
 *  Transaction test를 위한 createUser에 @Transactional 추가
 */

@Service("proxyUserService")
public class ProxyUserService implements UserService {

    private final UserService userService;

    public ProxyUserService(@Qualifier("userService") UserService userService) {
        this.userService = userService;
    }

    @Override
    public User createUser(User user) {
        return createUserThrowException(user);
    }

    @Transactional
    public User createUserThrowException(User user) {
        this.userService.createUser(user);
        throw new RuntimeException("occur create user runtime exception!!!");
    }
}

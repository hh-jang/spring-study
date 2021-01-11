package com.hhjang.spring.study.transaction;

import com.hhjang.spring.study.transaction.user.User;
import com.hhjang.spring.study.transaction.user.UserRepository;
import com.hhjang.spring.study.transaction.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service("isolationUserService")
public class IsolationUserService {

    private final UserService userService;
    private final UserRepository userRepository;

    public IsolationUserService(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    // dirty read test
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public boolean nonRepeatableRead(User user) {
        User alreadyCreatedUser = userService.findById(user.getId());
        User updatedUser = userService.updateUser(
                alreadyCreatedUser.getId(),
                "updatedEmail@google.com",
                "updatedName"
        );

        return !(updatedUser.getId().equals(alreadyCreatedUser.getId())
                && updatedUser.getEmail().equals(alreadyCreatedUser.getEmail())
                && updatedUser.getName().equals(alreadyCreatedUser.getName()));
    }

    // dirty read 재현 테스트 ->
    // transaction1         transaction2
    // insert
    // select
    //                      select and update
    // select
    // insert랑 다르다
}

package com.hhjang.spring.study.transaction;

import com.hhjang.spring.study.transaction.user.User;
import com.hhjang.spring.study.transaction.user.UserRepository;
import com.hhjang.spring.study.transaction.user.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionStatus;

import java.util.List;

@Service("isolationUserService")
public class IsolationUserService {

    private final UserService userService;
    private final UserRepository userRepository;

    public IsolationUserService(@Qualifier("transactionPropagationUserService") UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    // Non-Repeatable-Read 발생
    // TODO
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean nonRepeatableRead(User user) {
//        User beforeUpdateUser = userService.findById(user.getId());
//        userService.updateUser(
//                beforeUpdateUser.getId(),
//                "updatedEmail@google.com",
//                "updatedName"
//        );
//
//        User afterUpdateUser = userService.findById(user.getId());
//
//        return !(afterUpdateUser.getId().equals(afterUpdateUser.getId())
//                && afterUpdateUser.getEmail().equals(afterUpdateUser.getEmail())
//                && afterUpdateUser.getName().equals(afterUpdateUser.getName()));
        return false;
    }

    // REPEATABLE_READ에서 Phanttom Read를 발생
    // TODO why SERIALIZE에서도 발생하는지 확인
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public boolean phantomRead(User user) {
        List<User> beforeUserList = userService.findAll();

        userRepository.save(user);

        List<User> afterUserList = userService.findAll();

        return beforeUserList.size() != afterUserList.size();
    }
}

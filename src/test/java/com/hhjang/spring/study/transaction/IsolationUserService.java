package com.hhjang.spring.study.transaction;

import com.hhjang.spring.study.transaction.user.User;
import com.hhjang.spring.study.transaction.user.UserRepository;
import com.hhjang.spring.study.transaction.user.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service("isolationUserService")
public class IsolationUserService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final EntityManager em;

    public IsolationUserService(@Qualifier("transactionPropagationUserService") UserService userService, UserRepository userRepository, EntityManager em) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.em = em;
    }

    // READ_COMMITTED에서 Non-Repeatable-Read 발생
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public boolean nonRepeatableRead_read_committed(User user) throws InterruptedException {
        User beforeUpdateUser = userService.findById(user.getId());
        userService.updateUser(
                beforeUpdateUser.getId(),
                "updatedEmail@google.com",
                "updatedName"
        );
        // 영속성 컨텍스트에 존재하는 유저의 1차 캐싱을 지우기 위해 초기화
        em.clear();

        User afterUpdateUser = userService.findById(1);

        return !(beforeUpdateUser.getEmail().equals(afterUpdateUser.getEmail())
                && beforeUpdateUser.getName().equals(afterUpdateUser.getName()));
    }

    // REPEATABLE_READ에서 Non-Repeatable-Read 발생하지 않는
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public boolean nonRepeatableReadRepeatableRead(User user) throws InterruptedException {
        User beforeUpdateUser = userService.findById(user.getId());
        userService.updateUser(
                beforeUpdateUser.getId(),
                "updatedEmail@google.com",
                "updatedName"
        );
        // 영속성 컨텍스트에 존재하는 유저의 1차 캐싱을 지우기 위해 초기화
        em.clear();

        User afterUpdateUser = userService.findById(user.getId());

        return (beforeUpdateUser.getEmail().equals(afterUpdateUser.getEmail())
                && beforeUpdateUser.getName().equals(afterUpdateUser.getName()));
    }

    // REPEATABLE_READ에서 Phanttom Read를 발생
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public boolean phantomReadRepeatableRead(User user) {
        List<User> beforeUserList = userService.findAll();

        User createdUser = userService.createUser(user);
        createdUser.setName("updatedName");
        userRepository.save(createdUser);

        List<User> afterUserList = userService.findAll();

        return beforeUserList.size() != afterUserList.size();
    }
}

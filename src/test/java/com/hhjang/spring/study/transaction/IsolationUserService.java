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

    // Non-Repeatable-Read 발생
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public boolean nonRepeatableRead(User user) throws InterruptedException {
        User beforeUpdateUser = userService.findById(1);
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

    // REPEATABLE_READ에서 Phanttom Read를 발생
    // TODO why SERIALIZE에서도 발생하는지 확인
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean phantomRead(User user) {
        List<User> beforeUserList = userService.findAll();

        userRepository.save(user);
        em.flush();


        List<User> afterUserList = userService.findAll();

        return beforeUserList.size() != afterUserList.size();
    }
}

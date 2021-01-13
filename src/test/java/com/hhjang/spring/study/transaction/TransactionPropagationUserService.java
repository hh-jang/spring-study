package com.hhjang.spring.study.transaction;

import com.hhjang.spring.study.transaction.user.User;
import com.hhjang.spring.study.transaction.user.UserRepository;
import com.hhjang.spring.study.transaction.user.UserServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("transactionPropagationUserService")
public class TransactionPropagationUserService extends UserServiceImpl {

    public TransactionPropagationUserService(UserRepository userRepository) {
        super(userRepository);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public User createUser(User user) {
        return super.createUser(user);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public User updateUser(Integer id, String afterEmail, String afterName) {
        return super.updateUser(id, afterEmail, afterName);
    }
}

package com.hhjang.spring.study.transaction.user;

import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    @Override
    public User updateUser(final Integer id, final String afterEmail, final String afterName) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("user not fount"));
        user.setEmail(afterEmail);
        user.setName(afterName);

        User updatedUser = userRepository.save(user);
        return updatedUser;
    }

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("user not fount"));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}

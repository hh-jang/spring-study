package com.hhjang.spring.study.transaction.user;

public interface UserService {
    User createUser(User user);
    User updateUser(Integer id, String afterEmail, String afterName);
    User findById(Integer id);
}

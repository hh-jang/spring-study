package com.hhjang.spring.study.transaction.user;

import java.util.List;

public interface UserService {
    User createUser(User user);
    User updateUser(Integer id, String afterEmail, String afterName);
    User findById(Integer id);
    List<User> findAll();
}

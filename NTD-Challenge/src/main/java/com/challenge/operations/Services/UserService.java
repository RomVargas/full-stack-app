package com.challenge.operations.Services;

import java.util.List;
import java.util.Optional;

import com.challenge.operations.Entities.User;
import com.challenge.operations.Entities.UserRequest;

public interface UserService {
    List<User> findAll();
    Optional<User> findById(Long id);
    User save(User user);
    Optional<User> update(UserRequest user, Long id);
    void remove(UserRequest user, Long id);
}

package com.challenge.operations.Services.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.operations.Entities.User;
import com.challenge.operations.Entities.UserRequest;
import com.challenge.operations.Repositories.UserRepository;
import com.challenge.operations.Services.UserService;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly=true)
    public List<User> findAll() {
        return (List<User>) repository.findAll();    
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
       return repository.findById(id); 
    }

    @Override
    @Transactional
    public User save(User user) {
        String passwordBC = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordBC);
        return  repository.save(user);
    }

    @Override
    @Transactional
    public Optional<User> update(UserRequest user, Long id) {
        Optional<User> o = this.findById(id);
        User userOptional = null;
        if (o.isPresent()) {
            User userDb = o.orElseThrow();
            userDb.setUsername(user.getUsername());
            userDb.setEmail(user.getEmail());
            userOptional = this.save(userDb);
        }
        return Optional.ofNullable(userOptional);
    }

    @Override
    public void remove(UserRequest user, Long id) {
        Optional<User> o= this.findById(id);
        if (o.isPresent()) {
            User userDb = o.orElseThrow();
            userDb.setStatus("inactive");
            userDb.setDeleted(true);
            repository.save(userDb);
        }
    }
}

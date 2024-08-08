package com.challenge.operations.Repositories;

import com.challenge.operations.Entities.User;
import com.google.common.base.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);

    //@Query("select u from Users where u.username = ?1")
    //Optional<User>getUserByUsername(String username);
}

package com.challenge.operations.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.operations.Repositories.UserRepository;
import com.google.common.base.Optional;

@Service
public class JPAUserDetailsService implements UserDetailsService{

    @Autowired
    private UserRepository repository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<com.challenge.operations.Entities.User> o = repository.findByUsername(username);
        
        if (!o.isPresent()) {
            throw new UsernameNotFoundException("User do not exist");
        }
        com.challenge.operations.Entities.User user = o.get();

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new User(user.getUsername(), 
        user.getPassword(), 
        true, 
        true,
        true,
        true, 
        authorities);
    }
    
}

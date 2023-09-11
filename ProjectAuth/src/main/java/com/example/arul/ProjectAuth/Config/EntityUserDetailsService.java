package com.example.arul.ProjectAuth.Config;

import com.example.arul.ProjectAuth.Entity.UserEntity;
import com.example.arul.ProjectAuth.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EntityUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = repository.findByUsername(username);
        return userEntity.map(EntityUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(username + "User not found..!"));
    }
}

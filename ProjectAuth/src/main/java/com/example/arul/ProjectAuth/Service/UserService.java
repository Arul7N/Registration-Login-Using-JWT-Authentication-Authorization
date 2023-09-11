package com.example.arul.ProjectAuth.Service;

import com.example.arul.ProjectAuth.Config.EntityUserDetailsService;
import com.example.arul.ProjectAuth.Entity.UserEntity;
import com.example.arul.ProjectAuth.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserEntity saveUser(UserEntity userEntity){
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return repository.save(userEntity);
    }


}

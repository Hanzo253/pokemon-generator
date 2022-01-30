package com.project2.pokemongenerator.service;

import com.project2.pokemongenerator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    public User findUserByEmailAddress(String email) {
//        return userRepository.findUserByEmailAddress(email);
//    }
}

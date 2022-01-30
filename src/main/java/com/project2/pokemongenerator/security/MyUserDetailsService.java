package com.project2.pokemongenerator.security;

import com.project2.pokemongenerator.model.User;
import com.project2.pokemongenerator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MyUserDetailsService implements UserDetailsService {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findUserByEmailAddress(email);

        if (user == null) {
            throw new UsernameNotFoundException("Username not found in database");
        }
        
        return new MyUserDetails(user);
    }
}

package com.project2.pokemongenerator.security;

import com.project2.pokemongenerator.model.User;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserDetails implements UserDetails {

    private User user;
    private String userName;
    private String password;
    private String emailAddress;

    public MyUserDetails() {

    }

    public MyUserDetails(User user) {
        this.user = user;
    }
}

package com.project2.pokemongenerator.service;

import com.project2.pokemongenerator.exceptions.InformationExistsException;
import com.project2.pokemongenerator.model.Request.LoginRequest;
import com.project2.pokemongenerator.model.Response.LoginResponse;
import com.project2.pokemongenerator.model.User;
import com.project2.pokemongenerator.repository.UserRepository;
import com.project2.pokemongenerator.security.jwt.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private JWTUtils jwtUtils;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void setJwtUtils(JWTUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    // create new user and save it in the database
    public User createUser(User userObject) {
        if (!userRepository.existsByEmailAddress(userObject.getEmailAddress())) {
            userObject.setPassword(passwordEncoder.encode(userObject.getPassword()));
            return userRepository.save(userObject);
        } else {
            throw new InformationExistsException("user with email address " + userObject.getEmailAddress() + " already exists.");
        }
    }

    // checks if user login information is correct and returns the jwt key
    public ResponseEntity<?> loginUser(LoginRequest loginRequest){
        System.out.println("service calling loginUser ==>");
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        final String JWT = jwtUtils.generateToken(userDetails);
        return ResponseEntity.ok(new LoginResponse(JWT));
    }

    public User findUserByEmailAddress(String email) {
        return userRepository.findUserByEmailAddress(email);
    }
}

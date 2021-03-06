package com.project2.pokemongenerator.controller;

import com.project2.pokemongenerator.model.Request.LoginRequest;
import com.project2.pokemongenerator.model.User;
import com.project2.pokemongenerator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/users")
public class UserController {
    private UserService userService;
    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/register")
    public User createUser(@RequestBody User userObject) {
        return userService.createUser(userObject);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        System.out.println("calling loginUser");
        return userService.loginUser(loginRequest);
    }

    @GetMapping("/list")
    public Iterable<User> listUsers() {
        return userService.listUsers();
    }

    @GetMapping("/{username}")
    public User getUser(@PathVariable String username) {
        return userService.getUser(username);
    }

    @PutMapping("/changepassword")
    public User changePassword(@RequestBody User userObject) {
        return userService.changePassword(userObject);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser() {
        userService.deleteUserById();
        return ResponseEntity.ok().body("Deleting user...");
    }
}

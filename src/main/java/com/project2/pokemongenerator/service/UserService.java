package com.project2.pokemongenerator.service;

import com.project2.pokemongenerator.exceptions.IncorrectUserException;
import com.project2.pokemongenerator.exceptions.InformationExistsException;
import com.project2.pokemongenerator.exceptions.InformationNotFoundException;
import com.project2.pokemongenerator.model.Pokemon;
import com.project2.pokemongenerator.model.Request.LoginRequest;
import com.project2.pokemongenerator.model.Response.LoginResponse;
import com.project2.pokemongenerator.model.User;
import com.project2.pokemongenerator.repository.PokemonRepository;
import com.project2.pokemongenerator.repository.UserRepository;
import com.project2.pokemongenerator.security.MyUserDetails;
import com.project2.pokemongenerator.security.jwt.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService implements FavoritePokemon {
    private UserRepository userRepository;
    private PokemonRepository pokemonRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private JWTUtils jwtUtils;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setPokemonRepository(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
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
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        final String JWT = jwtUtils.generateToken(userDetails);
        return ResponseEntity.ok(new LoginResponse(JWT));
    }

    public User findUserByEmailAddress(String email) {
        return userRepository.findUserByEmailAddress(email);
    }

    @Override
    public User getUser(String username) {
        return userRepository.findUserByUserName(username);
    }

    public Iterable<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public User addFavoritePokemon(String username, Long pokemonId) {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Pokemon pokemon = pokemonRepository.findByIdAndUserId(pokemonId, userDetails.getUser().getId());
        if (pokemon == null) {
            throw new InformationNotFoundException("pokemon with id " + pokemonId + " not found.");
        }
//        Pokemon pokemon = pokemonRepository.findById(pokemonId).get();
        User user = getUser(username);
        if (user == null) {
            throw new InformationNotFoundException("username of " + username + " not found.");
        }
        if (pokemon.getUser().getUserName() != user.getUserName()) {
            throw new IncorrectUserException("This pokemon does not belong to the user " + username);
        }
        List<Pokemon> pokemonAdded = user.getFavoritePokemonList();
        if (pokemonAdded.isEmpty()) {
            user.addFavoritePokemon(pokemon);
//            System.out.println("Pokemon: " + " " + pokemon);
//            System.out.println("PokemonIdCheck: " + " " + pokemonIdCheck);
        } else {
            System.out.println(pokemonAdded);
//            System.out.println(user.getFavoritePokemonListSize());
            for (int i = 0; i <= user.getFavoritePokemonListSize() - 1; i++) {
                System.out.println(pokemonAdded.get(i));
                System.out.println(pokemon);
                if (pokemonAdded.get(i) == pokemon) {
                    throw new InformationExistsException("pokemon with name " + pokemon.getName() + " already exists in this list.");
                }
            }
            user.addFavoritePokemon(pokemon);
        }
        return userRepository.save(user);
    }
}

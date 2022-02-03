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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
            // throw exception if the user already exists
            throw new InformationExistsException("user with email address " + userObject.getEmailAddress() + " already exists.");
        }
    }

    // checks if user login information is correct and returns the jwt key
    public ResponseEntity<?> loginUser(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        System.out.println(userDetails);
        final String JWT = jwtUtils.generateToken(userDetails);
        return ResponseEntity.ok(new LoginResponse(JWT));
    }

    // changes the user's password
    public User changePassword(@RequestBody User userObject) {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findUserByUserName(userDetails.getUser().getUserName());
        System.out.println("User: " + user);
        System.out.println("UserDetails: " + userDetails);
        if (user == null) {
            // throw exception if the user does not exist
            throw new InformationNotFoundException("User not found.");
        } else {
//            System.out.println(user);
//            System.out.println(userObject.getPassword());
            user.setPassword(passwordEncoder.encode(userObject.getPassword()));
            return userRepository.save(user);
        }
    }

    // locates user by their email address
    public User findUserByEmailAddress(String email) {
        return userRepository.findUserByEmailAddress(email);
    }

    // getter for the user
    @Override
    public User getUser(String username) {
        if (userRepository.findUserByUserName(username) == null) {
            // throw exception if the user does not exist
            throw new InformationNotFoundException("User not found.");
        } else {
            return userRepository.findUserByUserName(username);
        }
    }

    // returns a list of all users in the users database
    public Iterable<User> listUsers() {
        if (userRepository.findAll().size() == 0) {
            // throw exception if there are no users in the list
            throw new InformationNotFoundException("No users found.");
        } else {
            return userRepository.findAll();
        }
    }

    // deletes the user by their id
    public void deleteUserById() {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findById(userDetails.getUser().getId());
        if (user.isEmpty()) {
            // throw exception if the user does not exist
            throw new InformationNotFoundException("User not found.");
        } else {
            if (!user.get().getPokemonList().isEmpty()) {
                // throw exception if the user does not have an empty pokemonList
                throw new InformationExistsException("User needs to empty their pokemonList and favoritePokemonList in order to be deleted");
            } else {
                userRepository.deleteById(userDetails.getUser().getId());
            }
        }
    }

    // adds a pokemon to a user's favorite pokemon list
    @Override
    public User addFavoritePokemon(String username, Long pokemonId) {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Pokemon pokemon = pokemonRepository.findByIdAndUserId(pokemonId, userDetails.getUser().getId());
        if (pokemon == null) {
            // throw exception if the pokemon id does not exist
            throw new InformationNotFoundException("pokemon with id " + pokemonId + " not found.");
        }
//        Pokemon pokemon = pokemonRepository.findById(pokemonId).get();
        User user = getUser(username);
        if (user == null) {
            // throw exception if the user does not have an empty pokemonList
            throw new InformationNotFoundException("username of " + username + " not found.");
        }
        if (pokemon.getUser().getUserName() != user.getUserName()) {
            // throw exception if the user does not own this pokemon
            throw new IncorrectUserException("This pokemon does not belong to the user " + username);
        }
        List<Pokemon> pokemonAdded = user.getFavoritePokemonList(); // pokemon added to the favorite pokemon list
        if (pokemonAdded.isEmpty()) {
            user.addFavoritePokemon(pokemon);
//            System.out.println("Pokemon: " + " " + pokemon);
//            System.out.println("PokemonIdCheck: " + " " + pokemonIdCheck);
        } else {
//            System.out.println(pokemonAdded);
//            System.out.println(user.getFavoritePokemonListSize());
            for (int i = 0; i <= user.getFavoritePokemonListSize() - 1; i++) {
//                System.out.println(pokemonAdded.get(i));
//                System.out.println(pokemon);
                if (pokemonAdded.get(i) == pokemon) {
                    // throw exception if the pokemon is already in the favorite pokemon list
                    throw new InformationExistsException("pokemon with name " + pokemon.getName() + " already exists in this list.");
                }
            }
            user.addFavoritePokemon(pokemon);
        }
        return userRepository.save(user);
    }
}

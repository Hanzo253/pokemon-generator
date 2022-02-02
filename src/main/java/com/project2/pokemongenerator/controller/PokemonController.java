package com.project2.pokemongenerator.controller;

import com.project2.pokemongenerator.exceptions.InformationNotFoundException;
import com.project2.pokemongenerator.model.Pokemon;
import com.project2.pokemongenerator.model.User;
import com.project2.pokemongenerator.service.PokemonService;
import com.project2.pokemongenerator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api") // means http://localhost:9092/api/
public class PokemonController {
    private UserService userService;
    private PokemonService pokemonService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setPokemonService(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @PostMapping("/pokemon/")
    public Pokemon createPokemon(@RequestBody Pokemon pokemonObject) {
        System.out.println("creating new pokemon....");
        return pokemonService.createPokemon(pokemonObject);
    }

    @GetMapping("/pokemon/")
    public List<Pokemon> getAllPokemon() {
        System.out.println("getting all pokemon...");
        return pokemonService.getAllPokemon();
    }

    @GetMapping("/pokemon/{pokemonId}")
    public Optional<Pokemon> getPokemon(@PathVariable(value = "pokemonId") Long pokemonId) {
        System.out.println("getting pokemon with an id of " + pokemonId);
        return pokemonService.getPokemon(pokemonId);
    }

    @PutMapping("/pokemon/{pokemonId}")
    public Pokemon updatePokemon(@PathVariable(value = "pokemonId") Long pokemonId, @RequestBody Pokemon pokemonObject) {
        System.out.println("updating pokemon with an id of " + pokemonId);
        return pokemonService.updatePokemon(pokemonId, pokemonObject);
    }

    @PutMapping("/pokemon/{pokemonId}/moves")
    public Pokemon updatePokemonMoves(@PathVariable(value = "pokemonId") Long pokemonId, @RequestBody Pokemon pokemonObject) {
        System.out.println("updating pokemon's moves with an id of " + pokemonId);
        return pokemonService.updatePokemonMoves(pokemonId, pokemonObject);
    }

    @PutMapping("/pokemon/{pokemonId}/{username}")
    public User addFavoritePokemon(@PathVariable String username, @PathVariable Long pokemonId) {
        return userService.addFavoritePokemon(username, pokemonId);
    }

    @DeleteMapping("/pokemon/{pokemonId}")
    public ResponseEntity<String> deletePokemon(@PathVariable(value = "pokemonId") Long pokemonId) {
        System.out.println("deleting pokemon with id " + pokemonId);
        pokemonService.deletePokemon(pokemonId);
        return ResponseEntity.ok().body("Deleting pokemon with id " + pokemonId);
    }
}

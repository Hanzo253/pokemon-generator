package com.project2.pokemongenerator.controller;

import com.project2.pokemongenerator.model.Pokemon;
import com.project2.pokemongenerator.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api") // means http://localhost:9092/api/
public class PokemonController {
    private PokemonService pokemonService;

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
        return pokemonService.getPokemon(pokemonId);
    }
}

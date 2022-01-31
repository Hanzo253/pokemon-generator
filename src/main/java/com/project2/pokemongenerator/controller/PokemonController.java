package com.project2.pokemongenerator.controller;

import com.project2.pokemongenerator.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api") // means http://localhost:9092/api/
public class PokemonController {
    private PokemonService pokemonService;

    @Autowired
    public void setPokemonService(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }
}

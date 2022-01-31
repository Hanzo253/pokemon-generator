package com.project2.pokemongenerator.service;

import com.project2.pokemongenerator.exceptions.InformationExistsException;
import com.project2.pokemongenerator.exceptions.InformationNotFoundException;
import com.project2.pokemongenerator.model.Pokemon;
import com.project2.pokemongenerator.repository.PokemonRepository;
import com.project2.pokemongenerator.security.MyUserDetails;
import jdk.jfr.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class PokemonService {
//    @Autowired
    private PokemonRepository pokemonRepository;

    @Autowired
    public void setPokemonRepository(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    public Pokemon createPokemon(Pokemon pokemonObject) {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Pokemon pokemon = pokemonRepository.findByUserIdAndName(userDetails.getUser().getId(), pokemonObject.getName());

        if (pokemon != null) {
            throw new InformationExistsException("pokemon with name " + pokemon.getName() + " already exists.");
        } else {
            pokemonObject.setUser(userDetails.getUser());
            return pokemonRepository.save(pokemonObject);
        }
    }

    public List<Pokemon> getAllPokemon() {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Pokemon> pokemons = pokemonRepository.findByUserId(userDetails.getUser().getId());

        if (pokemons.isEmpty()) {
            throw new InformationNotFoundException("no pokemon found for user id " + userDetails.getUser().getId() + " not found.");
        } else {
            return pokemons;
        }
    }

    public Optional<Pokemon> getPokemon(Long pokemonId) {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Pokemon pokemon = pokemonRepository.findByIdAndUserId(pokemonId, userDetails.getUser().getId());
        if (pokemon == null) {
            throw new InformationNotFoundException("pokemon with id " + pokemonId + "not found.");
        } else {
            return Optional.ofNullable(pokemon);
        }
    }

//    public Pokemon updatePokemon(Long pokemonId, @RequestBody Pokemon pokemonObject) {
//        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Pokemon pokemon = pokemonRepository.findByIdAndUserId(pokemonId, userDetails.getUser().getId());
//
//        if (pokemon == null) {
//            throw new InformationNotFoundException();
//        }
//    }
}

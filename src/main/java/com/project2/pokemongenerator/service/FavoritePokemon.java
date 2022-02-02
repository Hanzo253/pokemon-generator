package com.project2.pokemongenerator.service;

import com.project2.pokemongenerator.model.Pokemon;
import com.project2.pokemongenerator.model.User;

public interface FavoritePokemon {
    public User getUser(String username);
    public User addFavoritePokemon(String username, Long pokemonId);
}

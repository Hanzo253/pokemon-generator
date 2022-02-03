package com.project2.pokemongenerator.service;

import com.project2.pokemongenerator.model.Pokemon;
import com.project2.pokemongenerator.model.User;

public interface FavoritePokemon {
    // get the user by username
    public User getUser(String username);

    // add favorite pokemon with pokemon id and username
    public User addFavoritePokemon(Long pokemonId);
}

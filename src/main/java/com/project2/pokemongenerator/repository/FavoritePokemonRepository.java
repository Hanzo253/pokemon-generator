package com.project2.pokemongenerator.repository;

import com.project2.pokemongenerator.model.Pokemon;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FavoritePokemonRepository extends CrudRepository<Pokemon, Integer> {
    List<Pokemon> findByName(String name);
}

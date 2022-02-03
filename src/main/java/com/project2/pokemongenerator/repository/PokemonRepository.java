package com.project2.pokemongenerator.repository;

import com.project2.pokemongenerator.model.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PokemonRepository extends JpaRepository<Pokemon, Long> {
    // find pokemon by the user's id
    List<Pokemon> findByUserId(Long userId);

    // find pokemon by the user's id and pokemon's name
    Pokemon findByUserIdAndName(Long userId, String name);

    // find pokemon by the user's id and pokemon's id
    Pokemon findByIdAndUserId(Long pokemonId, Long id);
}

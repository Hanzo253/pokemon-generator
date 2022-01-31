package com.project2.pokemongenerator.repository;

import com.project2.pokemongenerator.model.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PokemonRepository extends JpaRepository<Pokemon, Long> {
    List<Pokemon> findByUserId(Long userId);
    Pokemon findByUserIdAndName(Long userId, String name);
    Pokemon findByIdAndUserId(Long categoryId, Long id);
}

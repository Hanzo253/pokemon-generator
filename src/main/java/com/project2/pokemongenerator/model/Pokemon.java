package com.project2.pokemongenerator.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "pokemon")
public class Pokemon {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String type;

    @Column
    private String gender;

    @Column
    private int generation;

    @Type(type = "string-array")
    @Column(columnDefinition = "text[]")
    private String[] moves;

    @Column
    private boolean isLegendary;

    @Column
    private boolean isShiny;

    @Column
    private boolean isFavorite;

    public Pokemon() {

    }

    public Pokemon(Long id, String name, String type, String gender, int generation, String[] moves, boolean isLegendary, boolean isShiny, boolean isFavorite) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.gender = gender;
        this.generation = generation;
        this.moves = moves;
        this.isLegendary = isLegendary;
        this.isShiny = isShiny;
        this.isFavorite = isFavorite;
    }
}

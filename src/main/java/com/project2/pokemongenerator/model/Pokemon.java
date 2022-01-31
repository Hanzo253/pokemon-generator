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
}

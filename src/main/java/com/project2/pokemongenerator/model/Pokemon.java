package com.project2.pokemongenerator.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Arrays;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getGeneration() {
        return generation;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }

    public String[] getMoves() {
        return moves;
    }

    public void setMoves(String[] moves) {
        this.moves = moves;
    }

    public boolean getIsLegendary() {
        return isLegendary;
    }

    public void setIsLegendary(boolean isLegendary) {
        this.isLegendary = isLegendary;
    }

    public boolean getIsShiny() {
        return isShiny;
    }

    public void setIsShiny(boolean isShiny) {
        this.isShiny = isShiny;
    }

    public boolean getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", gender='" + gender + '\'' +
                ", generation=" + generation +
                ", moves=" + Arrays.toString(moves) +
                ", isLegendary=" + isLegendary +
                ", isShiny=" + isShiny +
                ", isFavorite=" + isFavorite +
                '}';
    }
}

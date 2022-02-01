package com.project2.pokemongenerator.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "pokemon")
@TypeDef(name = "string-array", typeClass = StringArrayType.class)
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "favorite_pokemon", joinColumns = {@JoinColumn(name = "pokemon_id")}, inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
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

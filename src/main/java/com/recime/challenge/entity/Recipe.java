package com.recime.challenge.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table
@Getter
@Setter
public class Recipe {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long recipeId;
    private String name;
    private String description;
    private boolean vegetarian;
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeIngredient> ingredients;
    private Integer servings;
    private String instructions;
    //private Long photo;
}

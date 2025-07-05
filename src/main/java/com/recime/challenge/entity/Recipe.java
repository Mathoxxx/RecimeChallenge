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
    private String recipeId;
    private String name;
    private boolean vegetarian;
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeIngredient> ingredients;
    private Integer servings;
    @ElementCollection
    private List<String> instructions;
    //private Long photo;
}

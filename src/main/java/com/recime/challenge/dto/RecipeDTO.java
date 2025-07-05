package com.recime.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDTO {
    private Long recipeId;
    private String name;
    private String description;
    private Integer servings;
    private boolean vegetarian; //TODO calculate field
    private List<IngredientDTO> ingredients;
    private String instructions;
}

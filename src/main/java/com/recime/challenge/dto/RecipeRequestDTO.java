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
public class RecipeRequestDTO {
    private String name;
    private String description;
    private Integer servings;
    private List<RecipeIngredientDTO> ingredients;
    private List<String> instructions;
    //TODO: falta foto
}

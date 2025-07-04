package com.recime.challenge;

import com.recime.challenge.dto.IngredientDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Recipe {

    @Id
    private String recipeId;
    private String recipeName;
    private List<IngredientDTO> ingredients;
    private Integer servings;
    private List<String> steps;
    private Long photo;


}

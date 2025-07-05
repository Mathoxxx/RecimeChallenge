package com.recime.challenge.service;

import com.recime.challenge.dto.CreateRecipeRequestDTO;
import com.recime.challenge.dto.RecipeDTO;
import java.util.List;
import java.util.Optional;

/**
 * Recipe Service Interface
 */
public interface RecipeService {

    /**
     * Create a new recipe
     * @param recipeDTO     new recipe object
     * @return              RecipeDTO
     */
    public RecipeDTO createRecipe(CreateRecipeRequestDTO recipeDTO);

    /**
     * Retrieve recipe by identifier
     * @param id            recipe identifier
     * @return RecipeDTO    recipe
     */
    public Optional<RecipeDTO> getRecipeById(Long id);

    /**
     * Retrieve all recipes
     * @return              List<RecipeDTO>
     */
    public List<RecipeDTO> getAllRecipes();

    /**
     * Modify existing recipe
     * @param id            recipe identifier
     * @param recipeDTO     new recipe params
     * @return RecipeDTO    modified recipe
     */
    public RecipeDTO updateRecipe(Long id, RecipeDTO recipeDTO);


    /**
     * Delete existing recipe
     * @param id            recipe identifier
     */
    public void deleteRecipe(Long id);
}

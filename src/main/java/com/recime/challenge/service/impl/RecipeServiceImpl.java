package com.recime.challenge.service.impl;

import com.recime.challenge.dto.CreateRecipeRequestDTO;
import com.recime.challenge.dto.RecipeDTO;
import com.recime.challenge.dto.RecipeIngredientDTO;
import com.recime.challenge.entity.Ingredient;
import com.recime.challenge.entity.Recipe;
import com.recime.challenge.entity.RecipeIngredient;
import com.recime.challenge.error.ResourceNotFoundException;
import com.recime.challenge.mapper.RecipeMapper;
import com.recime.challenge.repository.IngredientRepository;
import com.recime.challenge.repository.RecipeRepository;
import com.recime.challenge.service.RecipeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.stream;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeMapper recipeMapper;

    public RecipeServiceImpl(final RecipeRepository repository,
                             final IngredientRepository ingredientRepository,
                             final RecipeMapper recipeMapper){
        this.recipeRepository = repository;
        this.ingredientRepository = ingredientRepository;
        this.recipeMapper = recipeMapper;
    }

    @Override
    public RecipeDTO createRecipe(CreateRecipeRequestDTO recipeDTO) {
        List<RecipeIngredient> recipeIngredients = new ArrayList<>();
        final Recipe recipe = recipeMapper.toEntity(recipeDTO);

        for (RecipeIngredient ri : recipe.getIngredients()) {
            Long ingredientId = ri.getIngredient().getId();
            Ingredient ingredient = ingredientRepository.findById(ingredientId)
                    .orElseThrow(() -> new RuntimeException("Ingredient not found"));

            ri.setIngredient(ingredient);
            ri.setRecipe(recipe);
        }

        recipe.setVegetarian(isVegetarian(recipe));

        Recipe saved = recipeRepository.save(recipe);

        return recipeMapper.toDTO(saved);
    }

    @Override
    public List<RecipeDTO> getAllRecipes() {
        return recipeMapper.toDTOs(recipeRepository.findAll());
    }

    @Override
    public Optional<RecipeDTO> getRecipeById(Long id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        return recipe.map(recipeMapper::toDTO);
        //TODO THROW A 404 WHEN NO RECIPE IS FOUND
    }

    @Override
    public RecipeDTO updateRecipe(Long id, RecipeDTO recipeDTO) {
        Recipe recipe = recipeMapper.toEntity(recipeDTO);
        return recipeMapper.toDTO(recipeRepository.save(recipe));
    }

    @Override
    public void deleteRecipe(Long id) {
        if (!recipeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recipe not found, id: " + id);
        }
        recipeRepository.deleteById(id);
    }

    private boolean isVegetarian(Recipe recipe) {
        return true;
    }
}

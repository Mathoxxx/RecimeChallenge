package com.recime.challenge.service.impl;

import com.recime.challenge.dto.RecipeFilterParamsDTO;
import com.recime.challenge.dto.RecipeIngredientDTO;
import com.recime.challenge.dto.RecipeRequestDTO;
import com.recime.challenge.dto.RecipeDTO;
import com.recime.challenge.entity.Ingredient;
import com.recime.challenge.entity.Recipe;
import com.recime.challenge.entity.RecipeIngredient;
import com.recime.challenge.error.ResourceNotFoundException;
import com.recime.challenge.mapper.RecipeMapper;
import com.recime.challenge.repository.IngredientRepository;
import com.recime.challenge.repository.RecipeRepository;
import com.recime.challenge.service.RecipeService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Recipe Service Implementation
 */
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeMapper recipeMapper;

    /**
     * Class constructor
     * @param repository recipeRepository
     * @param ingredientRepository ingredientRepository
     * @param recipeMapper recipeMapper
     */
    public RecipeServiceImpl(final RecipeRepository repository,
                             final IngredientRepository ingredientRepository,
                             final RecipeMapper recipeMapper){
        this.recipeRepository = repository;
        this.ingredientRepository = ingredientRepository;
        this.recipeMapper = recipeMapper;
    }

    /**
     * Create a new recipe function
     * @param recipeDTO     new recipe object
     * @return created recipe
     */
    @Override
    public RecipeDTO createRecipe(RecipeRequestDTO recipeDTO) {
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

    /**
     * Retrieve all recipes
     * @return list of all recipes
     */

    @Override
    public List<RecipeDTO> getAllRecipes() {
        return recipeMapper.toDTOs(recipeRepository.findAll());
    }

    /**
     * REtrieve recipe by identifier
     * @param id            recipe identifier
     * @return RecipeDTO
     */
    @Override
    public Optional<RecipeDTO> getRecipeById(Long id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        return recipe.map(recipeMapper::toDTO);
        //TODO THROW A 404 WHEN NO RECIPE IS FOUND
    }

    /**
     * Update recipe method
     * @param id            recipe identifier
     * @param recipeDTO     new recipe params
     * @return modified recipe
     */
    @Override
    public RecipeDTO updateRecipe(Long id, RecipeRequestDTO recipeDTO) {
        Recipe existingRecipe = recipeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found, id: " + id));
        //TODO THROW A 404 WHEN NO RECIPE IS FOUND

        // Replace all fields
        existingRecipe.setName(recipeDTO.getName());
        existingRecipe.setServings(recipeDTO.getServings());
        existingRecipe.setInstructions(recipeDTO.getInstructions());
        existingRecipe.setDescription(recipeDTO.getDescription());

        existingRecipe.getIngredients().clear();
        for (RecipeIngredientDTO ingredientDTO : recipeDTO.getIngredients()) {
            Ingredient ingredient = ingredientRepository.findById(ingredientDTO.getIngredientId())
                    .orElseThrow(() -> new ResourceNotFoundException("Recipe not found, id: " + ingredientDTO.getIngredientId()));

            RecipeIngredient ri = new RecipeIngredient();
            ri.setIngredient(ingredient);
            ri.setQuantity(ingredientDTO.getQuantity());
            ri.setRecipe(existingRecipe);
            existingRecipe.getIngredients().add(ri);
        }

        existingRecipe.setVegetarian(isVegetarian(existingRecipe));
        Recipe modifiedRecipe = recipeRepository.save(existingRecipe);

        return recipeMapper.toDTO(modifiedRecipe);
    }

    /**
     * Delete recipe method
     * @param id            recipe identifier
     */
    @Override
    public void deleteRecipe(Long id) {
        if (!recipeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recipe not found, id: " + id);
        }
        recipeRepository.deleteById(id);
    }

    /**
     * filterRecipes implementation
     * @param filters recipe filters
     * @return filtered list of recipes
     */
    @Override
    public List<RecipeDTO> filterRecipes(RecipeFilterParamsDTO filters){
        List<Recipe> allRecipes = recipeRepository.findAll();
        return recipeMapper.toDTOs(allRecipes.stream()
                .filter(r -> filters.getVegetarian() == null || r.isVegetarian() == filters.getVegetarian())
                .filter(r -> filters.getMinServings() == null || r.getServings() >= filters.getMinServings())
                .filter(r -> filters.getMaxServings() == null || r.getServings() <= filters.getMaxServings())
                .filter(r -> matchesContentSearch(r, filters.getContentSearch()))
                .filter(r -> matchesIncludeIngredients(r, filters.getIncludeIngredientsIds()))
                .filter(r -> matchesExcludeIngredients(r, filters.getExcludeIngredientsIds()))
                .collect(Collectors.toList()));
    }

    /**
     * Validate if a recipe is vegetarian
     * @param recipe RecipeEntity
     * @return true/false
     */
    private boolean isVegetarian(Recipe recipe) {
        for (RecipeIngredient recipeIngredient : recipe.getIngredients()) {
            if(!recipeIngredient.getIngredient().isVegetarian()) return false;
        }
        return true;
    }

    /**
     * Validates if recipe has certain ingredients
     * @param recipe RecipeEntity
     * @param includeIngredientIds ids of ingredients
     * @return true/false
     */
    private boolean matchesIncludeIngredients(Recipe recipe, List<Long> includeIngredientIds) {
        if (includeIngredientIds == null || includeIngredientIds.isEmpty()) return true;

        Set<Long> ingredientIds = recipe.getIngredients().stream()
                .map(ri -> ri.getIngredient().getId())
                .collect(Collectors.toSet());

        return ingredientIds.containsAll(includeIngredientIds);
    }

    /**
     * Validates if recipe doesn't have certain ingredients
     * @param recipe RecipeEntity
     * @param excludeIngredientIds ids of ingredients
     * @return true/false
     */
    private boolean matchesExcludeIngredients(Recipe recipe, List<Long> excludeIngredientIds) {
        if (excludeIngredientIds == null || excludeIngredientIds.isEmpty()) return true;

        Set<Long> ingredientIds = recipe.getIngredients().stream()
                .map(ri -> ri.getIngredient().getId())  // Assuming Ingredient has getId() method
                .collect(Collectors.toSet());

        return Collections.disjoint(ingredientIds, excludeIngredientIds);
    }

    /**
     * Validates if recipe name or instructions contain contentSearch
     * @param recipe RecipeEntity
     * @param contentSearch content
     * @return true/false
     */

    private boolean matchesContentSearch(Recipe recipe, String contentSearch) {
        return contentSearch == null ||
                recipe.getName().toLowerCase().contains(contentSearch.toLowerCase()) ||
                recipe.getInstructions().toLowerCase().contains(contentSearch.toLowerCase());
    }
}

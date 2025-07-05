package com.recime.challenge.controller;

import com.recime.challenge.dto.RecipeFilterParamsDTO;
import com.recime.challenge.dto.RecipeRequestDTO;
import com.recime.challenge.dto.RecipeDTO;
import com.recime.challenge.service.impl.RecipeServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Recipe Controller
 */

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeServiceImpl recipeService;

    /**
     * Constructor
     * @param recipeService the service layer for basic operations on recipes
     */
    public RecipeController(final RecipeServiceImpl recipeService){
        this.recipeService = recipeService;
    }

    /**
     * Obtain all recipes
     * @return List of RecipeDTO
     */
    @GetMapping("/")
    public ResponseEntity<List<RecipeDTO>> getAllRecipes() {
        List<RecipeDTO> recipes = recipeService.getAllRecipes();
        return ResponseEntity.ok(recipes);
    }

    /**
     * Retrieve one specific recipe
     * @param id  recipe identifier
     * @return RecipeDTO or NOT FOUND
     */
    @GetMapping("/{id}")
    public ResponseEntity<RecipeDTO> getRecipe(@PathVariable Long id) {
        return recipeService.getRecipeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Create new recipe
     * @param requestDTO Recipe  request
     * @return RecipeDTO or NOT FOUND
     */

    @PostMapping("")
    public ResponseEntity<RecipeDTO> addRecipe(@RequestBody RecipeRequestDTO requestDTO) {
        final RecipeDTO recipe = recipeService.createRecipe(requestDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(recipe);
    }

    /**
     * Update existing recipe
     * @param id recipe identifiar
     * @param recipeDTO recipe object
     * @return modified recipe or NOT FOUND
     */
    @PutMapping("/{id}")
    public ResponseEntity<RecipeDTO> updateRecipe(@PathVariable Long id, @RequestBody RecipeRequestDTO recipeDTO) {
        final RecipeDTO recipe = recipeService.updateRecipe(id, recipeDTO);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(recipe);
    }

    /**
     * DELETE operation
     * @param id identifier of recipe
     * @return ResponseEntity with OK or NOT FOUND
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<RecipeDTO> deleteRecipe(@PathVariable Long id) {
        Optional<RecipeDTO> existingRecipe = this.recipeService.getRecipeById(id);
        if(existingRecipe.isPresent()){
            this.recipeService.deleteRecipe(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Filter operation
     * @param filters filter parameters
     * @return ResponseEntity with OK or NOT FOUND
     */
    @PostMapping("/filter")
    public ResponseEntity<List<RecipeDTO>> filterRecipes(@RequestBody RecipeFilterParamsDTO filters) {
        List<RecipeDTO> result = recipeService.filterRecipes(filters);
        return ResponseEntity.ok(result);
    }


}

package com.recime.challenge.mapper;

import com.recime.challenge.dto.CreateRecipeRequestDTO;
import com.recime.challenge.dto.RecipeDTO;
import com.recime.challenge.dto.RecipeIngredientDTO;
import com.recime.challenge.entity.Ingredient;
import com.recime.challenge.entity.Recipe;
import com.recime.challenge.entity.RecipeIngredient;
import com.recime.challenge.repository.IngredientRepository;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = { IngredientMapper.class })
public interface RecipeMapper {

    // DTO → Entity (for creating a new recipe)
    RecipeDTO toRecipeDTO(CreateRecipeRequestDTO dto);

    Recipe toEntity(CreateRecipeRequestDTO dto, @Context IngredientRepository ingredientRepository);
    Recipe toEntity(RecipeDTO dto);

    RecipeDTO toDTO(Recipe recipe);

    List<RecipeDTO> toDTOs(List<Recipe> recipes);

    @AfterMapping
    default void setBackReferences(@MappingTarget Recipe recipe) {
        if (recipe.getIngredients() != null) {
            for (RecipeIngredient ri : recipe.getIngredients()) {
                ri.setRecipe(recipe);
            }
        }
    }

    RecipeIngredient toEntity(RecipeIngredientDTO dto, @Context IngredientRepository ingredientRepository);

    default Ingredient map(Long id, @Context IngredientRepository ingredientRepository) {
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ingredient ID not found: " + id));
    }

}

package com.recime.challenge.mapper;

import com.recime.challenge.dto.CreateRecipeRequestDTO;
import com.recime.challenge.dto.RecipeDTO;
import com.recime.challenge.entity.Recipe;
import com.recime.challenge.entity.RecipeIngredient;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = { IngredientMapper.class })
public interface RecipeMapper {

    // DTO → Entity (for creating a new recipe)
    RecipeDTO toRecipeDTO(CreateRecipeRequestDTO dto);
    Recipe toEntity(RecipeDTO dto);

    RecipeDTO toDTO(Recipe recipe);

    List<RecipeDTO> toDTOs(List<Recipe> recipes);

    @AfterMapping
    private void setBackReferences(@MappingTarget Recipe recipe) {
        if (recipe.getIngredients() != null) {
            for (RecipeIngredient ri : recipe.getIngredients()) {
                ri.setRecipe(recipe);
            }
        }
    }

}

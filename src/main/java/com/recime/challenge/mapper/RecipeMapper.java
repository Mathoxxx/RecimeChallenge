package com.recime.challenge.mapper;

import com.recime.challenge.dto.IngredientDTO;
import com.recime.challenge.dto.RecipeDTO;
import com.recime.challenge.dto.RecipeIngredientDTO;
import com.recime.challenge.dto.RecipeRequestDTO;
import com.recime.challenge.entity.Recipe;
import com.recime.challenge.entity.RecipeIngredient;
import org.mapstruct.AfterMapping;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;

/**
 * Mapper for recipe
 */
@Mapper(componentModel = "spring", uses = IngredientMapper.class)
public interface RecipeMapper {

    Recipe toEntity(RecipeRequestDTO dto);

    RecipeDTO toDTO(Recipe recipe);

    List<RecipeDTO> toDTOs(List<Recipe> recipes);

    @Mapping(target = "id", source = "ingredient.id")
    @Mapping(target = "name", source = "ingredient.name")
    @Mapping(target = "isVegetarian", source = "ingredient.vegetarian")
    IngredientDTO toIngredientDTO(RecipeIngredient recipeIngredient);

    @IterableMapping(qualifiedByName = "toRecipeIngredient")
    List<RecipeIngredient> toRecipeIngredients(List<RecipeIngredientDTO> ingredientDTOs);

    @Named("toRecipeIngredient")
    @Mapping(target = "ingredient", source = "ingredientId")
    @Mapping(target = "quantity", source = "quantity")
    RecipeIngredient toRecipeIngredient(RecipeIngredientDTO dto);

    @AfterMapping
    default void setBackReferences(@MappingTarget Recipe recipe) {
        if (recipe.getIngredients() != null) {
            for (RecipeIngredient ri : recipe.getIngredients()) {
                ri.setRecipe(recipe);
            }
        }
    }

}

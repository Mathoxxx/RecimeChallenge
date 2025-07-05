package com.recime.challenge.mapper;

import com.recime.challenge.dto.RecipeIngredientDTO;
import com.recime.challenge.entity.RecipeIngredient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * RecipeIngredientMapper
 */
@Mapper(componentModel = "spring", uses = IngredientMapper.class)
public interface RecipeIngredientMapper {

    @Mapping(target = "id", ignore = true)  // Ignore the 'id' for RecipeIngredient (auto-generated)
    @Mapping(target = "ingredient", source = "ingredientId")  // Map the 'id' from DTO to the 'ingredient' field (ingredientId)
    @Mapping(target = "recipe", ignore = true)  // Will be set manually in service layer
    RecipeIngredient toEntity(RecipeIngredientDTO dto);

    RecipeIngredientDTO toDTO(RecipeIngredient entity);

}
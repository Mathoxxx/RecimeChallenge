package com.recime.challenge.mapper;

import com.recime.challenge.dto.IngredientDTO;
import com.recime.challenge.entity.Ingredient;
import org.mapstruct.Mapper;

/**
 * Ingredient Mapper
 */
@Mapper(componentModel = "spring")
public interface IngredientMapper {

    Ingredient toIngredient(Long id);

}
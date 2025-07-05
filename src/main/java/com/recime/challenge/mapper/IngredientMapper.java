package com.recime.challenge.mapper;

import com.recime.challenge.dto.IngredientDTO;
import com.recime.challenge.entity.Ingredient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IngredientMapper {


    Ingredient toEntity(IngredientDTO dto);
    IngredientDTO toDTO(Ingredient entity);

    Ingredient toIngredient(Long id);

}
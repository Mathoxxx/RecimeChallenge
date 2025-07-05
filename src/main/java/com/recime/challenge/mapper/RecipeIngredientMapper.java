package com.recime.challenge.mapper;

import com.recime.challenge.dto.RecipeIngredientDTO;
import com.recime.challenge.entity.RecipeIngredient;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = IngredientMapper.class)
public interface RecipeIngredientMapper {

    RecipeIngredient toEntity(RecipeIngredientDTO dto);

    RecipeIngredientDTO toDTO(RecipeIngredient entity);

    List<RecipeIngredientDTO> toDTOs(List<RecipeIngredient> entities);
    List<RecipeIngredient> toEntities(List<RecipeIngredientDTO> dtos);
}
package com.recime.challenge.service.impl;

import com.recime.challenge.dto.RecipeDTO;
import com.recime.challenge.repository.RecipeRepository;
import com.recime.challenge.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository repository;

    @Override
    public RecipeDTO createRecipe(RecipeDTO recipe) {
        return repository.save(recipe);
    }

    @Override
    public List<RecipeDTO> getAllRecipes() {
        return repository.findAll();
    }

    @Override
    public Optional<RecipeDTO> getRecipeById(Long id) {
        return repository.findById(id);
    }

    @Override
    public RecipeDTO updateRecipe(Long id, RecipeDTO recipe) {
        return repository.save(recipe);
    }

    @Override
    public void deleteRecipe(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Recipe not found with id: " + id);
        }
        repository.deleteById(id);
    }
}

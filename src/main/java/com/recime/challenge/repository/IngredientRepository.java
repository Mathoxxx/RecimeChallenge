package com.recime.challenge.repository;

import com.recime.challenge.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for ingredients
 */
@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
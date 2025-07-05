package com.recime.challenge.repository;

import com.recime.challenge.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for recipes
 */
@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

}
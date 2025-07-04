package com.recime.challenge.repository;

import com.recime.challenge.dto.RecipeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<RecipeDTO, Long> {


    public List<RecipeDTO> findAll();


    public Optional<RecipeDTO> findById(Long id);





}
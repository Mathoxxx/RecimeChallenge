package com.recime.challenge.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * Ingredient Entity
 */
@Entity
@Data
public class Ingredient {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private boolean isVegetarian;
}
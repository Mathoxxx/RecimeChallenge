package com.recime.challenge.dto;

import lombok.Data;
import java.util.List;

/**
 * Recipe Filter Parameters Data Transfer Object
 */
@Data
public class RecipeFilterParamsDTO {
    private Boolean vegetarian;
    private Integer minServings;
    private Integer maxServings;
    private List<Long> includeIngredientsIds;
    private List<Long> excludeIngredientsIds;
    private String contentSearch;
}

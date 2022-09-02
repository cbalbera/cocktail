package com.cocktail_app.cocktail.Models;

import javax.persistence.*;

public class IngredientDTO {
    public enum ingredientType{
        ALCOHOL,
        LIQUEUR,
        MIXER,
        FRUIT, //TODO: how do we classify fruit juice (e.g. lime juice)
        VEGETABLE,
        SEASONING
    }
    private Long id;

    private String name;
    private ingredientType type;

    // empty constructor
    IngredientDTO() {}

    // constructor with all items
    public IngredientDTO(Long id, String name, ingredientType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    // constructor with all items except ID
    public IngredientDTO(String name, ingredientType type) {
        this.name = name;
        this.type = type;
    }

    // constructor with only non-null / required items
    public IngredientDTO(String name) {
        this.name = name;
    }

    public Long getId() { return id; }

    public String getName() {
        return name;
    }

    public ingredientType getType() {
        return type;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(ingredientType type) {
        this.type = type;
    }
}

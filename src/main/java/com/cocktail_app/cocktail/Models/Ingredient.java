package com.cocktail_app.cocktail.Models;

import javax.persistence.*;

@Entity
@Table
public class Ingredient {
    private enum ingredientType{
        ALCOHOL,
        LIQUEUR,
        MIXER,
        FRUIT,
        VEGETABLE,
        SEASONING
    }

    @Id
    @SequenceGenerator(
            name = "cocktail_sequence",
            sequenceName = "cocktail_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "cocktail_sequence"
    )

    private String name;
    private ingredientType type;

    // constructor with all items
    public Ingredient(String name, ingredientType type) {
        this.name = name;
        this.type = type;
    }

    // constructor with only non-null / required items
    public Ingredient(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ingredientType getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(ingredientType type) {
        this.type = type;
    }
}

package com.cocktail_app.cocktail.Models;

import javax.persistence.*;

@Entity(name = "Ingredient")
@Table(name = "INGREDIENT")
public class Ingredient {
    public enum ingredientType{
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
    private int id;

    private String name;
    @Enumerated(EnumType.ORDINAL)
    @Column(columnDefinition = "enum")
    private ingredientType type;

    // empty constructor
    Ingredient() {}

    // constructor with all items
    public Ingredient(String name, ingredientType type) {
        this.name = name;
        this.type = type;
    }

    // constructor with only non-null / required items
    public Ingredient(String name) {
        this.name = name;
    }

    public int getId() { return id; }

    public String getName() {
        return name;
    }

    public ingredientType getType() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(ingredientType type) {
        this.type = type;
    }
}

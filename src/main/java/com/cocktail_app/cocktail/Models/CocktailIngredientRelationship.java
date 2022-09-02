package com.cocktail_app.cocktail.Models;

import javax.persistence.*;

@Entity(name = "CocktailIngredientRelationship")
@Table(name = "COCKTAILINGREDIENTRELATIONSHIP")
public class CocktailIngredientRelationship {
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
    private Long id;
    @Column(name="cocktailId")
    private Long cocktailId;
    @Column(name="ingredientId")
    private Long ingredientId;

    //empty constructor
    public CocktailIngredientRelationship() {
    }

    //constructor with only cocktail and ingredient IDs
    public CocktailIngredientRelationship(Long cocktailId, Long ingredientId) {
        this.cocktailId = cocktailId;
        this.ingredientId = ingredientId;
    }

    //constructor with all items
    public CocktailIngredientRelationship(Long id, Long cocktailId, Long ingredientId) {
        this.id = id;
        this.cocktailId = cocktailId;
        this.ingredientId = ingredientId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCocktailId() {
        return cocktailId;
    }

    public void setCocktailId(Long cocktailId) {
        this.cocktailId = cocktailId;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }
}

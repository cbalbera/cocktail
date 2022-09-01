package com.cocktail_app.cocktail.Repositories;

import com.cocktail_app.cocktail.Models.CocktailIngredientRelationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CocktailIngredientRelationshipRepo extends JpaRepository<CocktailIngredientRelationship,Long> {
    // returns list of CocktailId
    public List<Long> findByCocktailId(Long cocktailId);

    // returns list of IngredientId
    public List<Long> findByIngredientId(Long ingredientId);
}

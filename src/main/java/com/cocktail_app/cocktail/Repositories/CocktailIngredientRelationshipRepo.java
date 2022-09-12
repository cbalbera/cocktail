package com.cocktail_app.cocktail.Repositories;

import com.cocktail_app.cocktail.Models.CocktailIngredientRelationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CocktailIngredientRelationshipRepo extends JpaRepository<CocktailIngredientRelationship,Long> {
    // returns list of CocktailIngredientRelationship
    @Query(value="SELECT u FROM CocktailIngredientRelationship u WHERE cocktailId = ?1")
    List<CocktailIngredientRelationship> findByCocktailId(Long cocktailId);

    // returns list of CocktailIngredientRelationship
    @Query(value="SELECT u FROM CocktailIngredientRelationship u WHERE ingredientId = ?1")
    List<CocktailIngredientRelationship> findByIngredientId(Long ingredientId);
}

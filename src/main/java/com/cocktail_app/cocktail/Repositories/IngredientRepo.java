package com.cocktail_app.cocktail.Repositories;

import com.cocktail_app.cocktail.Models.IngredientDB;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientRepo extends JpaRepository<IngredientDB,Long> {

    List<IngredientDB> findByName(String name);
}

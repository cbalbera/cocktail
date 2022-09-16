package com.cocktail_app.cocktail.Repositories;

import com.cocktail_app.cocktail.Models.IngredientDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepo extends JpaRepository<IngredientDB,Long> {
    List<IngredientDB> findByName(String name);
    boolean existsById(Long id);
}

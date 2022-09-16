package com.cocktail_app.cocktail.Repositories;

import com.cocktail_app.cocktail.Models.CocktailDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CocktailRepo extends JpaRepository<CocktailDB,Long> {
    List<CocktailDB> findByName(String name);

    boolean existsById(Long id);

}

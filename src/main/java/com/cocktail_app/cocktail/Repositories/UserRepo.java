package com.cocktail_app.cocktail.Repositories;

import com.cocktail_app.cocktail.Models.CocktailDB;
import com.cocktail_app.cocktail.Models.CocktailIngredientRelationship;
import com.cocktail_app.cocktail.Models.IngredientDB;
import com.cocktail_app.cocktail.Models.UserDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<UserDB,Long> {
    // all queries that require inputs within are parameterized to protect against SQL/HQL injection
    @Query(value="SELECT u FROM UserDB u WHERE email =:email")
    UserDB findByEmail(@Param("email") String email);

    @Query(value="SELECT u FROM UserDB u WHERE id =:id")
    UserDB findUserByUUID(@Param("id") UUID id);

    @Query(value="DELETE FROM UserDB u WHERE id =:id")
    void deleteUserByUUID(@Param("id") UUID id);

    @Query
    boolean existsByUserId(UUID userId);
    @Query
    boolean existsByEmail(String email);

    @Query(value="SELECT u FROM CocktailDB u WHERE id=:id")
    CocktailDB findCocktailById(@Param("id") Long id);

    @Query(value="SELECT u FROM CocktailDB u")
    List<CocktailDB> getAllCocktails();

    @Query(value="SELECT u FROM CocktailIngredientRelationship u")
    List<CocktailIngredientRelationship> getAllRelationships();

    @Query(value="SELECT u FROM CocktailIngredientRelationship u WHERE cocktailId =:id")
    List<CocktailIngredientRelationship> getCocktailRelationships(@Param("id") Long id);

    @Query(value="SELECT u FROM IngredientDB u WHERE ingredient_id=:id")
    IngredientDB findIngredientById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("UPDATE UserDB u SET u.pantry = :pantry WHERE id=:id")
    void updatePantry(@Param("pantry") String pantry, @Param("id") UUID id);
    // https://docs.spring.io/spring-data/data-jpa/docs/current/reference/html/#jpa.modifying-queries

    @Query(value="SELECT u FROM CocktailIngredientRelationship u WHERE ingredient_id=:id")
    List<CocktailIngredientRelationship> getAllRelationshipsByIngredient(@Param("id") Long id);

    /*
    @Query(value="SELECT CocktailDB.name, CocktailDB.id " +
            "FROM CocktailDB INNER JOIN CocktailIngredientRelationship" +
            "ON CocktailDB.id = CocktailIngredientRelationship.cocktailId" +
            "INNER JOIN IngredientDB ON CocktailIngredientRelationship.ingredientId" +
            "= IngredientDB.id",nativeQuery = true)
    List<CocktailDB> getAllCocktailIngredientRelationships();


    @Query(value="SELECT * FROM CocktailIngredientRelationship WHERE ingredientId =:ingredientId")
    List<CocktailIngredientRelationship> findRelationshipsByIngredientId(@Param("ingredientId") Long ingredientId);

    @Query(value="SELECT * FROM CocktailIngredientRelationship WHERE ingredientId =:ingredientId AND cocktailId = :cocktailId")
    List<CocktailIngredientRelationship> findRelationshipsByIngredientIdAndCocktailId(@Param("ingredientId") Long ingredientId, @Param("cocktailId") Long cocktailId);

     */
}

package com.cocktail_app.cocktail.Repositories;

import com.cocktail_app.cocktail.Models.CocktailDB;
import com.cocktail_app.cocktail.Models.UserDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
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
    boolean existsByEmail(String email);

    @Query(value="Select u FROM CocktailDB u WHERE id=:id")
    CocktailDB findCocktailByID(@Param("id") Long id);

}

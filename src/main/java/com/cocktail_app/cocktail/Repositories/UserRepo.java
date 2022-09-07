package com.cocktail_app.cocktail.Repositories;

import com.cocktail_app.cocktail.Models.UserDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserDB,Long> {
}

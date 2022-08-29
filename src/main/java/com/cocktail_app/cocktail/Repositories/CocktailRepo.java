package com.cocktail_app.cocktail.Repositories;

import com.cocktail_app.cocktail.Models.Cocktail;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CocktailRepo extends JpaRepository<Cocktail,Long> {

    //TODO
    // ADD/RELOCATE ALL FUNCTIONS THAT CONNECT TO DATABASE & HANDLE ALL NECESSARY TRANSFORMATIONS
    // USE SERVICE CLASS FUNCTIONS WHERE NEC TO MODIFY DATA TYPES, ETC
    // GOAL: CONTROLLER CLASS SHOULD BE AS SIMPLE AS POSSIBLE

    List<Cocktail> findByName(String name);

}

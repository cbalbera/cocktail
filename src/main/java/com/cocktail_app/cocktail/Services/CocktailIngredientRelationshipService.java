package com.cocktail_app.cocktail.Services;

import com.cocktail_app.cocktail.Models.CocktailIngredientRelationship;
import com.cocktail_app.cocktail.Repositories.CocktailIngredientRelationshipRepo;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManagerFactory;
import java.util.Iterator;
import java.util.List;

public class CocktailIngredientRelationshipService {
    public CocktailIngredientRelationshipRepo relationshipRepo;

    //TODO: examine if we should have a superclass for all Services that creates the sessionfactory (next 10 lines)
    private SessionFactory hibernateFactory;

    @Autowired
    public CocktailIngredientRelationshipService(EntityManagerFactory factory, CocktailIngredientRelationshipRepo relationshipRepo) {
        if(factory.unwrap(SessionFactory.class) == null){
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.hibernateFactory = factory.unwrap(SessionFactory.class);
        this.relationshipRepo = relationshipRepo;
    }

    public List<CocktailIngredientRelationship> getRelationships() {
        return relationshipRepo.findAll();
    }

    public List<Long> getCocktailsByIngredientId(Long ingredientId) {
        return relationshipRepo.findByIngredientId(ingredientId);
    }

    public List<Long> getIngredientsByCocktailId(Long cocktailId) {
        return relationshipRepo.findByIngredientId(cocktailId);
    }

    public CocktailIngredientRelationship addRelationship(Long cocktailId, Long ingredientId) {
        CocktailIngredientRelationship relationship = new CocktailIngredientRelationship(cocktailId,ingredientId);
        relationshipRepo.save(relationship);
        return relationship;
    }

    public void addIngredientsToOneCocktail(Long cocktailId, List<Long> ingredientIds) {
        Iterator<Long> Iterator = ingredientIds.iterator();
        while (Iterator.hasNext()) {
            addRelationship(cocktailId,Iterator.next());
        }
    }

    public void deleteRelationship(Long relationshipId) {
        relationshipRepo.deleteById(relationshipId);
    }
}

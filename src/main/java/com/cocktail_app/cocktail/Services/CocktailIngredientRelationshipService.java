package com.cocktail_app.cocktail.Services;

import com.cocktail_app.cocktail.Models.CocktailIngredientRelationship;
import com.cocktail_app.cocktail.Models.IngredientDB;
import com.cocktail_app.cocktail.Repositories.CocktailIngredientRelationshipRepo;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@Component
public class CocktailIngredientRelationshipService {
    public CocktailIngredientRelationshipRepo relationshipRepo;

    //TODO: examine if we should have a superclass for all Services to abstract away creation of the sessionfactory (next 10 lines)
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
        List<CocktailIngredientRelationship> relationships = relationshipRepo.findByIngredientId(ingredientId);
        if (relationships == null) {
            return null;
        }
        List<Long> output = new ArrayList<Long>();
        ListIterator<CocktailIngredientRelationship> Iterator = relationships.listIterator();
        while (Iterator.hasNext()) {
            output.add(Iterator.next().getCocktailId());
        }
        return output;
    }

    //TODO: as with above,
    // handle case where cocktailId search returns an empty list
    public List<Long> getIngredientsByCocktailId(Long cocktailId) {
        List<CocktailIngredientRelationship> relationships = relationshipRepo.findByCocktailId(cocktailId);
        if (relationships == null) {
            return null;
        }
        List<Long> output = new ArrayList<Long>();
        ListIterator<CocktailIngredientRelationship> Iterator = relationships.listIterator();
        while (Iterator.hasNext()) {
            output.add(Iterator.next().getIngredientId());
        }
        return output;
    }

    public CocktailIngredientRelationship addRelationshipObject(CocktailIngredientRelationship relationship) {
        relationshipRepo.save(relationship);
        return relationship;
    }

    public CocktailIngredientRelationship addRelationshipDeconstructed(Long cocktailId, Long ingredientId) {
        CocktailIngredientRelationship relationship = new CocktailIngredientRelationship(cocktailId,ingredientId);
        relationshipRepo.save(relationship);
        return relationship;
    }

    public void addIngredientsToOneCocktail(Long cocktailId, List<Long> ingredientIds) {
        Iterator<Long> Iterator = ingredientIds.iterator();
        while (Iterator.hasNext()) {
            addRelationshipDeconstructed(cocktailId,Iterator.next());
        }
    }

    public void deleteRelationship(Long relationshipId) {
        relationshipRepo.deleteById(relationshipId);
    }
}

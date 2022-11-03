package com.cocktail_app.cocktail.Services;

import com.cocktail_app.cocktail.Helpers.CocktailConverter;
import com.cocktail_app.cocktail.Models.IngredientDB;
import com.cocktail_app.cocktail.Models.IngredientDTO;
import com.cocktail_app.cocktail.Repositories.IngredientRepo;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

@Component
public class IngredientService {

    public IngredientRepo ingredientRepo;

    //TODO: examine if we should have a superclass for all Services to abstract away creation of the sessionfactory (next 10 lines)
    private SessionFactory hibernateFactory;
    public CocktailConverter converter;

    @Autowired
    public IngredientService(EntityManagerFactory factory, IngredientRepo ingredientRepo, CocktailConverter converter) {
        if(factory.unwrap(SessionFactory.class) == null){
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.hibernateFactory = factory.unwrap(SessionFactory.class);
        this.ingredientRepo = ingredientRepo;
        this.converter = converter;
    }

    public List<IngredientDTO> getIngredients() {
        // similar to the analogous method in CocktailService, this may get expensive - O(N) time and O(N) space
        List<IngredientDB> ingredients = ingredientRepo.findAll();
        List<IngredientDTO> output = new ArrayList<IngredientDTO>();
        ListIterator<IngredientDB> Iterator = ingredients.listIterator();
        while (Iterator.hasNext()) {
            IngredientDTO ingredient = converter.convertIngredientDBToIngredientDTO(Iterator.next());
            output.add(ingredient);
        }
        return output;
    }

    public IngredientDB addIngredient(IngredientDB ingredient) {
        ingredientRepo.save(ingredient);
        return ingredient;
    }

    public List<IngredientDB> addIngredients(List<IngredientDB> ingredients) {
        ingredientRepo.saveAll(ingredients);
        return ingredients;
    }

    public void deleteIngredient(Long id) {
        ingredientRepo.deleteById(id);
    }

    //dangerous
    public void deleteIngredient() {
        ingredientRepo.deleteAll();
    }

    public IngredientDTO findIngredientById(Long id) {
        Optional<IngredientDB> ingredientOptional = ingredientRepo.findById(id);
        if (ingredientOptional.isPresent()) {
            IngredientDB IngredientDB = ingredientOptional.get();
            return converter.convertIngredientDBToIngredientDTO(IngredientDB);
        } else {
            return null;
        }
    }

    public List<IngredientDB> findCocktailsByName(String name) {
        return ingredientRepo.findByName(name);
    }

    public IngredientDB updateCocktail(IngredientDTO ingredient) {
        IngredientDB IngredientDB = converter.convertIngredientDTOToIngredientDB(ingredient);
        ingredientRepo.save(IngredientDB);
        return IngredientDB;
    }

    public boolean existsById(Long ingredientId) {
        return this.ingredientRepo.existsById(ingredientId);
    }

    // private methods

}

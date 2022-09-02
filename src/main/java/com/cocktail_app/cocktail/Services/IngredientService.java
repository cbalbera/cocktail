package com.cocktail_app.cocktail.Services;

import com.cocktail_app.cocktail.Models.CocktailDB;
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

    @Autowired
    public IngredientService(EntityManagerFactory factory, IngredientRepo ingredientRepo) {
        if(factory.unwrap(SessionFactory.class) == null){
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.hibernateFactory = factory.unwrap(SessionFactory.class);
        this.ingredientRepo = ingredientRepo;
    }

    public List<IngredientDTO> getIngredients() {
        // similar to the analogous method in CocktailService, this may get expensive - O(N) time and O(N) space
        List<IngredientDB> ingredients = ingredientRepo.findAll();
        List<IngredientDTO> output = new ArrayList<IngredientDTO>();
        ListIterator<IngredientDB> Iterator = ingredients.listIterator();
        while (Iterator.hasNext()) {
            IngredientDTO ingredient = convertIngredientDBToIngredientDTO(Iterator.next());
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
            return convertIngredientDBToIngredientDTO(IngredientDB);
        } else {
            return null;
        }
    }

    public List<IngredientDB> findCocktailsByName(String name) {
        return ingredientRepo.findByName(name);
    }

    public IngredientDB updateCocktail(IngredientDTO ingredient) {
        IngredientDB IngredientDB = convertIngredientDTOToIngredientDB(ingredient);
        ingredientRepo.save(IngredientDB);
        return IngredientDB;
    }

    public IngredientDTO convertIngredientDBToIngredientDTO(IngredientDB ingredient) {
        IngredientDTO.ingredientType type = ingredientTypeIntToEnum(ingredient.getType());
        return new IngredientDTO(
                ingredient.getId(),
                ingredient.getName(),
                type
        );
    }

    public IngredientDB convertIngredientDTOToIngredientDB(IngredientDTO ingredient) {
        int type = ingredientTypeEnumToInt(ingredient.getType());
        return new IngredientDB(
                ingredient.getId(),
                ingredient.getName(),
                type
        );
    }

    public IngredientDTO.ingredientType ingredientTypeIntToEnum(int type) {
        IngredientDTO.ingredientType output = IngredientDTO.ingredientType.ALCOHOL;
        switch(type) {
            case 0:
                break;
            case 1:
                output = IngredientDTO.ingredientType.LIQUEUR;
                break;
            case 2:
                output = IngredientDTO.ingredientType.MIXER;
                break;
            case 3:
                output = IngredientDTO.ingredientType.FRUIT;
                break;
            case 4:
                output = IngredientDTO.ingredientType.VEGETABLE;
                break;
            case 5:
                output = IngredientDTO.ingredientType.SEASONING;
                break;
            default:
                break;
        }
        return output;
    }
    public int ingredientTypeEnumToInt(IngredientDTO.ingredientType type) {
        int output = 0;
        switch(type) {
            case ALCOHOL:
                break;
            case LIQUEUR:
                output = 1;
                break;
            case MIXER:
                output = 2;
                break;
            case FRUIT:
                output = 3;
                break;
            case VEGETABLE:
                output = 4;
                break;
            case SEASONING:
                output = 5;
                break;
            default:
                break;
        }
        return output;
    }

}

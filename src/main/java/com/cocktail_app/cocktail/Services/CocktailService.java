package com.cocktail_app.cocktail.Services;

import com.cocktail_app.cocktail.Models.Cocktail;
import com.cocktail_app.cocktail.Models.CocktailDB;
import com.cocktail_app.cocktail.Repositories.CocktailRepo;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

@Component
public class CocktailService {
    //TODO
    // TRANSFORMATIONS/LOGIC BETWEEN FRONT-FACING DATA TRANSFER OBJECTS AND DB OBJECTS
    // FOR EX: CONVERSIONS BW ENUM AND INT

    public CocktailRepo cocktailRepo;

    private SessionFactory hibernateFactory;

    // TODO: TEST ME
    @Autowired
    public CocktailService(EntityManagerFactory factory, CocktailRepo cocktailRepo) {
        if(factory.unwrap(SessionFactory.class) == null){
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.hibernateFactory = factory.unwrap(SessionFactory.class); //https://stackoverflow.com/questions/25063995/spring-boot-handle-to-hibernate-sessionfactory
        this.cocktailRepo = cocktailRepo;
    }

    public List<Cocktail> getCocktails() {
        // this method may get expensive - O(N) time and space where N = # of cocktails in DB
        // the likelihood it is used frequently is relatively low, but something to monitor
        List<CocktailDB> cocktails = cocktailRepo.findAll();
        List<Cocktail> output = new ArrayList<Cocktail>();
        ListIterator<CocktailDB> Iterator = cocktails.listIterator();
        while (Iterator.hasNext()) {
            Cocktail cocktail = convertCocktailDBToCocktail(Iterator.next());
            output.add(cocktail);
        }
        return output;
    }

    public CocktailDB addCocktail(Cocktail cocktail) {
        CocktailDB cocktailDB = convertCocktailToCocktailDB(cocktail);
        cocktailRepo.save(cocktailDB);
        return cocktailDB;
    }

    public List<CocktailDB> addCocktails(List<CocktailDB> cocktails) {
        cocktailRepo.saveAll(cocktails);
        return cocktails;
    }

    public void deleteCocktail(Long id) {
        cocktailRepo.deleteById(id);
    }

    public Cocktail findCocktailById(Long id) {
        Optional<CocktailDB> cocktailOptional = cocktailRepo.findById(id);
        if (cocktailOptional.isPresent()) {
            CocktailDB cocktailDB = cocktailOptional.get();
            return convertCocktailDBToCocktail(cocktailDB);
        } else {
            return null;
        }
    }

    public List<Cocktail> findCocktailsByName(String name) {
        return cocktailRepo.findByName(name);
    }

    public CocktailDB updateCocktail(Cocktail cocktail) {
        CocktailDB cocktailDB = convertCocktailToCocktailDB(cocktail);
        cocktailRepo.save(cocktailDB);
        return cocktailDB;
    }

    public CocktailDB convertCocktailToCocktailDB(Cocktail cocktail) {
        int difficulty = difficultyEnumToInt(cocktail.getDifficulty());
        return new CocktailDB(
                cocktail.getName(),
                cocktail.getTools(),
                difficulty,
                cocktail.getInstructions(),
                cocktail.getTags()
        );
    }

    public Cocktail convertCocktailDBToCocktail(CocktailDB cocktail) {
        Cocktail.Difficulty difficulty = difficultyIntToEnum(cocktail.getDifficulty());
        return new Cocktail(
                cocktail.getName(),
                cocktail.getTools(),
                difficulty,
                cocktail.getInstructions(),
                cocktail.getTags()
        );
    }

    public int difficultyEnumToInt(Cocktail.Difficulty difficulty) {
        int output = 0;
        switch(difficulty) {
            case VERY_EASY:
                break;
            case EASY:
                output = 1;
                break;
            case MODERATE:
                output = 2;
                break;
            case DIFFICULT:
                output = 3;
                break;
            default:
                break;
        }
        return output;
    }

    public Cocktail.Difficulty difficultyIntToEnum(int difficulty) {
        Cocktail.Difficulty output = Cocktail.Difficulty.VERY_EASY;
        switch(difficulty) {
            case 0:
                break;
            case 1:
                output = Cocktail.Difficulty.EASY;
                break;
            case 2:
                output = Cocktail.Difficulty.MODERATE;
                break;
            case 3:
                output = Cocktail.Difficulty.DIFFICULT;
                break;
            default:
                break;
        }
        return output;
    }
}

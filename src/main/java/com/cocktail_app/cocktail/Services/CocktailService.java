package com.cocktail_app.cocktail.Services;

import com.cocktail_app.cocktail.Models.Cocktail;
import com.cocktail_app.cocktail.Repositories.CocktailRepo;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import java.util.List;
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
        return cocktailRepo.findAll();
    }

    public Cocktail addCocktail(Cocktail cocktail) {
        cocktailRepo.save(cocktail);
        return cocktail;
    }

    public List<Cocktail> addCocktails(List<Cocktail> cocktails) {
        cocktailRepo.saveAll(cocktails);
        return cocktails;
    }

    public void deleteCocktail(Long id) {
        cocktailRepo.deleteById(id);
    }

    public Cocktail findCocktailById(Long id) {
        Optional<Cocktail> cocktailOptional = cocktailRepo.findById(id);
        if (cocktailOptional.isPresent()) {
            return cocktailOptional.get();
        } else {
            return null;
        }
    }

    public List<Cocktail> findCocktailsByName(String name) {
        return cocktailRepo.findByName(name);
    }

    public Cocktail updateCocktail(Cocktail cocktail) {
        cocktailRepo.save(cocktail);
        return cocktail;
    }
}

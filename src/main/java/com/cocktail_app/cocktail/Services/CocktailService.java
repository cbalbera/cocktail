package com.cocktail_app.cocktail.Services;

import com.cocktail_app.cocktail.Helpers.CocktailConverter;
import com.cocktail_app.cocktail.Models.CocktailDTO;
import com.cocktail_app.cocktail.Models.CocktailDB;
import com.cocktail_app.cocktail.Repositories.CocktailRepo;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

import static java.lang.Character.isDigit;

@Component
public class CocktailService {

    public CocktailRepo cocktailRepo;
    private SessionFactory hibernateFactory;
    private CocktailConverter converter;

    @Autowired
    public CocktailService(EntityManagerFactory factory, CocktailRepo cocktailRepo, CocktailConverter converter) {
        if(factory.unwrap(SessionFactory.class) == null){
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.hibernateFactory = factory.unwrap(SessionFactory.class); //https://stackoverflow.com/questions/25063995/spring-boot-handle-to-hibernate-sessionfactory
        this.cocktailRepo = cocktailRepo;
        this.converter = converter;
    }

    public List<CocktailDTO> getCocktails() {
        // this method may get expensive - O(N) time and space where N = # of cocktails in DB
        // the likelihood it is used frequently is relatively low, but something to monitor
        List<CocktailDB> cocktails = cocktailRepo.findAll();
        List<CocktailDTO> output = new ArrayList<CocktailDTO>();
        ListIterator<CocktailDB> Iterator = cocktails.listIterator();
        while (Iterator.hasNext()) {
            CocktailDTO cocktail = converter.convertCocktailDBToCocktailDTO(Iterator.next());
            output.add(cocktail);
        }
        return output;
    }


    public CocktailDB addCocktail(CocktailDB cocktail) {
        cocktail.setInstructions(converter.addInstructionsDelimiter(cocktail.getInstructions()));
        cocktailRepo.save(cocktail);
        return cocktail;
    }

    public List<CocktailDB> addCocktails(List<CocktailDB> cocktails) {
        cocktailRepo.saveAll(cocktails);
        return cocktails;
    }

    public void deleteCocktail(Long id) {
        cocktailRepo.deleteById(id);
    }

    //dangerous
    public void deleteCocktails() {
        cocktailRepo.deleteAll();
    }

    public CocktailDTO findCocktailById(Long id) {
        Optional<CocktailDB> cocktailOptional = cocktailRepo.findById(id);
        if (cocktailOptional.isPresent()) {
            CocktailDB cocktailDB = cocktailOptional.get();
            return converter.convertCocktailDBToCocktailDTO(cocktailDB);
        } else {
            return null;
        }
    }

    public List<CocktailDB> findCocktailsByName(String name) {
        return cocktailRepo.findByName(name);
    }

    public CocktailDB updateCocktail(CocktailDTO cocktail) {
        CocktailDB cocktailDB = converter.convertCocktailDTOToCocktailDB(cocktail);
        cocktailRepo.save(cocktailDB);
        return cocktailDB;
    }

    // private methods

}

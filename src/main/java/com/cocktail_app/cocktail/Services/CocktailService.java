package com.cocktail_app.cocktail.Services;

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

    @Autowired
    public CocktailService(EntityManagerFactory factory, CocktailRepo cocktailRepo) {
        if(factory.unwrap(SessionFactory.class) == null){
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.hibernateFactory = factory.unwrap(SessionFactory.class); //https://stackoverflow.com/questions/25063995/spring-boot-handle-to-hibernate-sessionfactory
        this.cocktailRepo = cocktailRepo;
    }

    public List<CocktailDTO> getCocktails() {
        // this method may get expensive - O(N) time and space where N = # of cocktails in DB
        // the likelihood it is used frequently is relatively low, but something to monitor
        List<CocktailDB> cocktails = cocktailRepo.findAll();
        List<CocktailDTO> output = new ArrayList<CocktailDTO>();
        ListIterator<CocktailDB> Iterator = cocktails.listIterator();
        while (Iterator.hasNext()) {
            CocktailDTO cocktail = convertCocktailDBToCocktailDTO(Iterator.next());
            output.add(cocktail);
        }
        return output;
    }


    public CocktailDB addCocktail(CocktailDB cocktail) {
        cocktail.setInstructions(addInstructionsDelimiter(cocktail.getInstructions()));
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
            return convertCocktailDBToCocktailDTO(cocktailDB);
        } else {
            return null;
        }
    }

    public List<CocktailDB> findCocktailsByName(String name) {
        return cocktailRepo.findByName(name);
    }

    public CocktailDB updateCocktail(CocktailDTO cocktail) {
        CocktailDB cocktailDB = convertCocktailDTOToCocktailDB(cocktail);
        cocktailRepo.save(cocktailDB);
        return cocktailDB;
    }

    public CocktailDB convertCocktailDTOToCocktailDB(CocktailDTO cocktail) {
        int difficulty = difficultyEnumToInt(cocktail.getDifficulty());
        String instructions = instructionsToString(cocktail.getInstructions());
        return new CocktailDB(
                cocktail.getId(),
                cocktail.getName(),
                cocktail.getTools(),
                difficulty,
                instructions,
                cocktail.getTags(),
                cocktail.getGlassType(),
                cocktail.getIceType(),
                cocktail.getIsParent(),
                cocktail.getChildrenIDs(),
                cocktail.getIsChild(),
                cocktail.getParentID()
        );
    }

    public CocktailDTO convertCocktailDBToCocktailDTO(CocktailDB cocktail) {
        CocktailDTO.Difficulty difficulty = difficultyIntToEnum(cocktail.getDifficulty());
        List<String> instructions = parseCocktailInstructions(cocktail.getInstructions());
        return new CocktailDTO(
                cocktail.getId(),
                cocktail.getName(),
                cocktail.getTools(),
                difficulty,
                instructions,
                cocktail.getTags(),
                cocktail.getGlassType(),
                cocktail.getIceType(),
                cocktail.getIsParent(),
                cocktail.getChildrenIDs(),
                cocktail.getIsChild(),
                cocktail.getParentID()
        );
    }

    public int difficultyEnumToInt(CocktailDTO.Difficulty difficulty) {
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

    public CocktailDTO.Difficulty difficultyIntToEnum(int difficulty) {
        CocktailDTO.Difficulty output = CocktailDTO.Difficulty.VERY_EASY;
        switch(difficulty) {
            case 0:
                break;
            case 1:
                output = CocktailDTO.Difficulty.EASY;
                break;
            case 2:
                output = CocktailDTO.Difficulty.MODERATE;
                break;
            case 3:
                output = CocktailDTO.Difficulty.DIFFICULT;
                break;
            default:
                break;
        }
        return output;
    }

    public List<String> parseCocktailInstructions(String instructions) {
        // TODO: enforce this method's assumption that instructions strings will be delimited using a semicolon
        // note that this is handled in instructionsToString below, so only necessary for new instructions input
        List<String> output = new ArrayList<String>();
        char delimiter = '~';
        int priorLineStart = 0, i = 0, n = instructions.length();
        // add instructions line
        for(; i < n; i++) {
            if (instructions.charAt(i) == delimiter) {
                String toAdd = instructions.substring(priorLineStart,i);
                output.add(toAdd);
                priorLineStart = i+1;
            }
        }
        // add final instructions line, which will end at end of String and not have a delimiter
        String toAdd = instructions.substring(priorLineStart,i);
        output.add(toAdd);
        return output;
    }

    public String instructionsToString(List<String> instructions) {
        String output = String.join("~",instructions);
        return output;
    }

    // this parsing method may need to be made more robust as time goes on
    // it is also not efficient at present - O(N) time and O(N) space, really 2N additional space
    public String addInstructionsDelimiter(String instructions) {
        int n = instructions.length();
        if (n < 4) { return instructions; }
        char[] instructionsChars = instructions.toCharArray();
        char period = '.';
        char tilde = '~';
        // this makes the assumption that a number followed by a period signifies the end of a line
        for (int i = 3; i < n; i++) {
            if (isDigit(instructionsChars[i-1]) && instructionsChars[i] == period) {
                instructionsChars[i-2] = tilde;
            }
        }
        return new String(instructionsChars);
    }
}

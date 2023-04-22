package com.cocktail_app.cocktail.Helpers;

import com.cocktail_app.cocktail.Models.CocktailDB;
import com.cocktail_app.cocktail.Models.CocktailDTO;
import com.cocktail_app.cocktail.Models.IngredientDB;
import com.cocktail_app.cocktail.Models.IngredientDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Character.isDigit;

@Component
public class CocktailConverter {
    // Cocktail class conversion methods
    // DB > DTO
    public CocktailDB convertCocktailDTOToCocktailDB(CocktailDTO cocktail) {
        int difficulty = difficultyEnumToInt(cocktail.getDifficulty());
        String instructions = listStringToString(cocktail.getInstructions());
        String thumbnails = listStringToString(cocktail.getThumbnails());
        String ingredients = mapToString(cocktail.getIngredients());
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
                cocktail.getChildrenIds(),
                cocktail.getIsChild(),
                cocktail.getParentId(),
                cocktail.getBartenderId(),
                thumbnails,
                ingredients,
                cocktail.getIngredientCount()
        );
    }

    // DTO > DB
    public CocktailDTO convertCocktailDBToCocktailDTO(CocktailDB cocktail) {
        CocktailDTO.Difficulty difficulty = difficultyIntToEnum(cocktail.getDifficulty());
        List<String> instructions = parseStringToListString(cocktail.getInstructions());
        List<String> thumbnails = parseStringToListString(cocktail.getThumbnails());
        Map<Long,Boolean> ingredients = parseStringToMapString(cocktail.getIngredients());
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
                cocktail.getChildrenIds(),
                cocktail.getIsChild(),
                cocktail.getParentId(),
                cocktail.getBartenderId(),
                thumbnails,
                ingredients,
                cocktail.getIngredientCount()
        );
    }

    // enumeration conversion methods
    // int to enum
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

    // enum to int
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

    // methods for packaging to and from DB-friendly types for use in the above DB <> DTO conversions
    // String to List<String> for use with Instructions
    public List<String> parseStringToListString(String instructions) {
        // TODO: enforce this method's assumption that instructions strings will be delimited using a tilde
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

    // List<String> to String for use with Instructions
    public String listStringToString(List<String> instructions) {
        String output = String.join("~",instructions);
        return output;
    }

    // delimiter addition for initial data intake
    // this parsing method may need to be made more robust as time goes on
    // it is also not efficient at present - O(N) time and O(N) space, really 2N additional space
    public String addInstructionsDelimiter(String instructions) {
        int n = instructions.length();
        if (n < 4) { return instructions; }
        char[] instructionsChars = instructions.toCharArray();
        char period = '.';
        char tilde = '~';
        // this makes the assumption that a number followed by a period signifies the start of a new line
        for (int i = 3; i < n; i++) {
            if (isDigit(instructionsChars[i-1]) && instructionsChars[i] == period) {
                instructionsChars[i-2] = tilde;
            }
        }
        return new String(instructionsChars);
    }

    // String to List<Long>
    // used for items in User classes:
    // cocktailList, pantry, favoriteCocktails, favoriteBartenders
    public List<Long> parseStringToListLong(String string) {
        if (string == null) { return null; }
        // TODO: enforce this method's assumption that instructions strings will be delimited using a tilde
        // note that this is handled in instructionsToString below, so only necessary for new instructions input
        List<Long> output = new ArrayList<Long>();
        char delimiter = '~';
        int priorLineStart = 0, i = 0, n = string.length();
        // add instructions line
        for (; i < n; i++) {
            if (string.charAt(i) == delimiter) {
                Long toAdd = Long.parseLong(string.substring(priorLineStart, i));
                output.add(toAdd);
                priorLineStart = i + 1;
            }
        }
        // add final instructions line, which will end at end of String and not have a delimiter
        Long toAdd = Long.parseLong(string.substring(priorLineStart, i));
        output.add(toAdd);
        return output;
    }

    // List<Long> to String
    // used for items in User classes:
    // cocktailList, pantry, favoriteCocktails, favoriteBartenders
    public String listLongToString(List<Long> listLong) {
        if (listLong == null) { return null; }
        String output = listLong
                .stream()
                .map(String::valueOf)
                .collect(Collectors.joining("~"));
        return output;
    }

    // for ingredients
    // String to Map (keys)
    public Map<Long,Boolean> parseStringToMapString(String ingredients) {
        // TODO: enforce this method's assumption that ingredients strings will be delimited using a tilde
        // note that this is handled in instructionsToString below, so only necessary for new instructions input
        Map<Long,Boolean> output = new HashMap<>();
        char delimiter = '~';
        int priorLineStart = 0, i = 0, n = ingredients.length();
        // add instructions line
        for(; i < n; i++) {
            if (ingredients.charAt(i) == delimiter) {
                String toAdd = ingredients.substring(priorLineStart,i);
                Long id = Long.parseLong(toAdd);
                // important that boolean value is always false here - see UserService's update upon change function for details
                output.put(id,false);
                priorLineStart = i+1;
            }
        }
        // add final instructions line, which will end at end of String and not have a delimiter
        String toAdd = ingredients.substring(priorLineStart,i);
        Long id = Long.parseLong(toAdd);
        output.put(id,false);
        return output;
    }

    // Map (keys) to String
    // TODO this appears to work, but could use further testing
    public String mapToString(Map<Long,Boolean> map) {
        if (map == null) { return null; }
        String output = map
                .entrySet()
                .stream()
                .map(e -> e.getKey().toString())
                .collect(Collectors.joining("~"));
        return output;
    }

    // Ingredient type conversion methods and enumeration methods
    // IngredientDB to IngredientDTO
    public IngredientDTO convertIngredientDBToIngredientDTO(IngredientDB ingredient) {
        IngredientDTO.ingredientType type = ingredientTypeIntToEnum(ingredient.getType());
        return new IngredientDTO(
                ingredient.getId(),
                ingredient.getName(),
                type
        );
    }

    // DTO > DB
    public IngredientDB convertIngredientDTOToIngredientDB(IngredientDTO ingredient) {
        int type = ingredientTypeEnumToInt(ingredient.getType());
        return new IngredientDB(
                ingredient.getId(),
                ingredient.getName(),
                type
        );
    }

    // Ingredient type enumeration conversion methods
    // ingredientType int to enum
    private IngredientDTO.ingredientType ingredientTypeIntToEnum(int type) {
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

    // ingredientType enum to int
    private int ingredientTypeEnumToInt(IngredientDTO.ingredientType type) {
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

package com.cocktail_app.cocktail.Helpers;

import com.cocktail_app.cocktail.Models.CocktailDB;
import com.cocktail_app.cocktail.Models.CocktailDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
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
                thumbnails
        );
    }

    // DTO > DB
    public CocktailDTO convertCocktailDBToCocktailDTO(CocktailDB cocktail) {
        CocktailDTO.Difficulty difficulty = difficultyIntToEnum(cocktail.getDifficulty());
        List<String> instructions = parseStringToListString(cocktail.getInstructions());
        List<String> thumbnails = parseStringToListString(cocktail.getThumbnails());
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
                thumbnails
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
        // this makes the assumption that a number followed by a period signifies the end of a line
        for (int i = 3; i < n; i++) {
            if (isDigit(instructionsChars[i-1]) && instructionsChars[i] == period) {
                instructionsChars[i-2] = tilde;
            }
        }
        return new String(instructionsChars);
    }

    // String to List<Long>
    public List<Long> parseStringToListLong(String string) {
        if (string == null) { return null; }
        // TODO: enforce this method's assumption that instructions strings will be delimited using a semicolon
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
    public String listLongToString(List<Long> listLong) {
        if (listLong == null) { return null; }
        String output = listLong
                .stream()
                .map(String::valueOf)
                .collect(Collectors.joining("~"));
        return output;
    }
}

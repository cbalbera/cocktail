package com.cocktail_app.cocktail.Services;

import com.cocktail_app.cocktail.Models.*;
import com.cocktail_app.cocktail.Repositories.CocktailIngredientRelationshipRepo;
import com.cocktail_app.cocktail.Repositories.UserRepo;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserService {

    public UserRepo userRepo;

    //TODO: examine if we should have a superclass for all Services to abstract away creation of the sessionfactory (next 10 lines)
    private SessionFactory hibernateFactory;

    @Autowired
    public UserService(EntityManagerFactory factory, UserRepo userRepo) {
        if(factory.unwrap(SessionFactory.class) == null){
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.hibernateFactory = factory.unwrap(SessionFactory.class);
        this.userRepo = userRepo;
    }

    public List<UserDTO> getUsers() {
        // as with the same in Cocktail, Ingredient, this method is O(N) time and space and may get expensive
        List<UserDB> users = userRepo.findAll();
        List<UserDTO> output = new ArrayList<UserDTO>();
        ListIterator<UserDB> Iterator = users.listIterator();
        while (Iterator.hasNext()) {
            UserDTO user = convertUserDBToUserDTO(Iterator.next());
            output.add(user);
        }
        return output;
    }

    public UserDTO findUserById(Long id) {
        Optional<UserDB> userOptional = userRepo.findById(id);
        if (userOptional.isPresent()) {
            UserDB userDB = userOptional.get();
            return convertUserDBToUserDTO(userDB);
        } else {
            return null;
        }
    }

    public UserDB addUser(UserDTO user) {
        UserDB userDB = convertUserDTOToUserDB(user);
        userRepo.save(userDB);
        return userDB;
    }

    public UserDB updateCocktail(UserDTO user) {
        UserDB userDB = convertUserDTOToUserDB(user);
        userRepo.save(userDB);
        return userDB;
    }

    // private methods

    // class conversion methods
    // DB > DTO
    private UserDTO convertUserDBToUserDTO(UserDB user) {
        UserDTO.userType type = userTypeIntToEnum(user.getUserType());
        List<Long> cocktailList = parseStringToListLong(user.getCocktailList());
        List<Long> pantry = parseStringToListLong(user.getPantry());
        List<Long> favoriteCocktails = parseStringToListLong(user.getFavoriteCocktails());
        List<Long> favoriteBartenders = parseStringToListLong(user.getFavoriteBartenders());
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getHashedPassword(),
                type,
                cocktailList,
                pantry,
                favoriteCocktails,
                favoriteBartenders
        );
    }

    // DTO > DB
    private UserDB convertUserDTOToUserDB(UserDTO user) {
        int type = userTypeEnumToInt(user.getUserType());
        String cocktailList = listLongToString(user.getCocktailList());
        String pantry = listLongToString(user.getPantry());
        String favoriteCocktails = listLongToString(user.getFavoriteCocktails());
        String favoriteBartenders = listLongToString(user.getFavoriteBartenders());
        return new UserDB(
                user.getId(),
                user.getUsername(),
                user.getHashedPassword(),
                type,
                cocktailList,
                pantry,
                favoriteCocktails,
                favoriteBartenders
        );
    }

    // enumeration conversion methods
    // int to enum
    private UserDTO.userType userTypeIntToEnum(int type) {
        UserDTO.userType output = UserDTO.userType.USER;
        switch (type) {
            case 0:
                break;
            case 1:
                output = UserDTO.userType.BARTENDER;
                break;
            case 2:
                output = UserDTO.userType.ADMIN;
                break;
            default:
                break;
        }
        return output;
    }

    // enum to int
    private int userTypeEnumToInt(UserDTO.userType type) {
        int output = 0;
        switch(type) {
            case USER:
                break;
            case BARTENDER:
                output = 1;
                break;
            case ADMIN:
                output = 2;
                break;
            default:
                break;
        }
        return output;
    }

    // methods for packaging to and from DB-friendly types for use in the above DB <> DTO conversions
    // String to List<Long>
    private List<Long> parseStringToListLong(String string) {
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
    private String listLongToString(List<Long> listLong) {
        String output = listLong
                .stream()
                .map(String::valueOf)
                .collect(Collectors.joining("~"));
        return output;
    }


}

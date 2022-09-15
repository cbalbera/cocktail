package com.cocktail_app.cocktail.Services;

import com.cocktail_app.cocktail.Helpers.CocktailConverter;
import com.cocktail_app.cocktail.Models.*;
import com.cocktail_app.cocktail.Repositories.UserRepo;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class UserService {

    public UserRepo userRepo;
    //TODO: examine if we should have a superclass for all Services to abstract away creation of the sessionfactory (next 10 lines)
    private SessionFactory hibernateFactory;
    public CocktailConverter converter;

    @Autowired
    public UserService(EntityManagerFactory factory, UserRepo userRepo, CocktailConverter converter) {
        if(factory.unwrap(SessionFactory.class) == null){
            throw new NullPointerException("factory is not a hibernate factory");
        }
        this.hibernateFactory = factory.unwrap(SessionFactory.class);
        this.userRepo = userRepo;
        this.converter = converter;
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

    public UserDTO findUserById(UUID id) {
        System.out.println("UUID:");
        System.out.println(id);
        UserDB user = userRepo.findUserByUUID(id);
        System.out.println("user:");
        System.out.println(user);
        if (user != null) {
            System.out.println("match - returning user");
            return convertUserDBToUserDTO(user);
        } else {
            System.out.println("null/no match");
            return null;
        }
    }

    public UserDB addUser(UserDB user) {
        // add method uses setter, so this method includes pwd hashing
        userRepo.save(user);
        return user;
    }

    public UserDB updateCocktail(UserDTO user) {
        UserDB userDB = convertUserDTOToUserDB(user);
        userRepo.save(userDB);
        return userDB;
    }

    public void deleteUser(UUID userId) {
        userRepo.deleteUserByUUID(userId);
    }

    public boolean userLogIn(String email, String rawPassword) {
        UserDB user = userRepo.findByEmail(email);
        if (user == null) {
            return false;
        } else {
            Argon2PasswordEncoder encoder = new Argon2PasswordEncoder();
            return encoder.matches(rawPassword,user.getHashedPassword());
        }
    }

    //TODO: sanitize inputs to avoid SQL injection (as well as other common errors)
    // used short return type here to enable different types of rejection, although currently only checking by email
    public short createUser(UserDB user) {
        if (!userRepo.existsByEmail(user.getEmail())) {
            addUser(user);
            return 1;
        } else {
            return 0;
        }
    }

    public CocktailDB findCocktailById(Long cocktailId) {
        return this.userRepo.findCocktailById(cocktailId);
    }

    public List<CocktailDTO> getUserCocktails(UUID userId) {
        System.out.println("user ID is "+userId);
        UserDTO user = findUserById(userId);
        System.out.println("user:");
        System.out.println(user);
        List<CocktailDTO> output = new ArrayList<>();
        ListIterator<Long> iterator = user.getCocktailList().listIterator();
        while(iterator.hasNext()) {
            CocktailDB cocktailDB = findCocktailById(iterator.next());
            CocktailDTO cocktailDTO = converter.convertCocktailDBToCocktailDTO(cocktailDB);
            output.add(cocktailDTO);
        }
        return output;
    }

    public boolean existsById(UUID userId) {
        return this.userRepo.existsByUserId(userId);
    }

    public List<CocktailDTO> GetMakeableCocktails(UUID userId) {
        UserDB user = userRepo.findUserByUUID(userId);
        List<Long> pantry = converter.parseStringToListLong(user.getPantry());
        List<CocktailDTO> output = new ArrayList<>();
        List<CocktailDB> allCocktails = this.userRepo.getAllCocktails();
        //TODO: is it more efficient DB-wise to pull all relationships and then break down by cocktail in Java
        // or to pull relationships by cocktail when needed?  former is fewer calls to DB but an additional loop
        // per cocktail to determine its set of relationships; latter is more calls to DB but no add'l loop
        // for now, going with latter as afaik the DB query is faster in operational time?
        //List<CocktailIngredientRelationship> allRelationships = this.userRepo.getAllRelationships();

        // convert pantry to HashMap - one loop instead of N loops where N = no. of cocktails
        Map<Long,Integer> ingredientMap = new HashMap<>();
        for(int i = 0, n = pantry.size(); i < n; i++) {
            ingredientMap.put(pantry.get(i),1);
        }

        for (int i = 0, n = allCocktails.size(); i < n; i++) {
            CocktailDB cocktailDB = allCocktails.get(i);
            Long id = cocktailDB.getId();
            Boolean makeable = makeableHelper(id,0,ingredientMap);
            if(makeable) {
                output.add(converter.convertCocktailDBToCocktailDTO(cocktailDB));
            }
        }
        return output;
    }

    //TODO
    public List<CocktailDTO> GetAlmostMakeableCocktails(UUID userId) {
        UserDB user = userRepo.findUserByUUID(userId);
        List<Long> pantry = converter.parseStringToListLong(user.getPantry());
        List<CocktailDTO> output = new ArrayList<>();
        List<CocktailDB> allCocktails = this.userRepo.getAllCocktails();
        //TODO: is it more efficient DB-wise to pull all relationships and then break down by cocktail in Java
        // or to pull relationships by cocktail when needed?  former is fewer calls to DB but an additional loop
        // per cocktail to determine its set of relationships; latter is more calls to DB but no add'l loop
        // for now, going with latter as afaik the DB query is faster in operational time?
        //List<CocktailIngredientRelationship> allRelationships = this.userRepo.getAllRelationships();

        // convert pantry to HashMap - one loop instead of N loops where N = no. of cocktails
        Map<Long,Integer> ingredientMap = new HashMap<>();
        for(int i = 0, n = pantry.size(); i < n; i++) {
            ingredientMap.put(pantry.get(i),1);
        }

        for (int i = 0, n = allCocktails.size(); i < n; i++) {
            CocktailDB cocktailDB = allCocktails.get(i);
            Long id = cocktailDB.getId();
            Boolean makeable = makeableHelper(id,1,ingredientMap);
            if(makeable) {
                output.add(converter.convertCocktailDBToCocktailDTO(cocktailDB));
            }
        }
        return output;
    }

    // private methods

    // class conversion methods
    // DB > DTO
    private UserDTO convertUserDBToUserDTO(UserDB user) {
        UserDTO.userType type = userTypeIntToEnum(user.getUserType());
        List<Long> cocktailList = converter.parseStringToListLong(user.getCocktailList());
        List<Long> pantry = converter.parseStringToListLong(user.getPantry());
        List<Long> favoriteCocktails = converter.parseStringToListLong(user.getFavoriteCocktails());
        List<Long> favoriteBartenders = converter.parseStringToListLong(user.getFavoriteBartenders());
        return new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getHashedPassword(),
                type,
                cocktailList,
                pantry,
                favoriteCocktails,
                favoriteBartenders,
                user.getZipCode()
        );
    }

    // DTO > DB
    private UserDB convertUserDTOToUserDB(UserDTO user) {
        int type = userTypeEnumToInt(user.getUserType());
        String cocktailList = converter.listLongToString(user.getCocktailList());
        String pantry = converter.listLongToString(user.getPantry());
        String favoriteCocktails = converter.listLongToString(user.getFavoriteCocktails());
        String favoriteBartenders = converter.listLongToString(user.getFavoriteBartenders());
        return new UserDB(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getHashedPassword(),
                type,
                cocktailList,
                pantry,
                favoriteCocktails,
                favoriteBartenders,
                user.getZipCode()
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

    // this method is O(2^n * n) as written, with slight reduction possible by only evaluating sets
    // where there is at least 1 ALCOHOL or LIQUEUR
    // as such, this (and methods that use it, such as those to determine makeable cocktails) is to
    // be called strategically
    private List<List<Long>> getPowerSetPantry(List<Long> pantry) {
        List<List<Long>> output = new ArrayList<>();
        List<Long> empty = new ArrayList<>();
        output.add(empty);
        for(int i = 0, n = pantry.size(); i < n; i++) {
            for(int j = 0, p = output.size(); j < p; j++) {
                List<Long> toAdd = new ArrayList<>();
                toAdd.addAll(output.get(j));
                toAdd.add(pantry.get(i));
                output.add(toAdd);
            }
        }
        // remove empty set; this is necessary for the algo to function but will cause query to throw
        // error if empty set is passed
        output.remove(0);
        return output;
    }

    private boolean makeableHelper(Long cocktailId, int missingIngredientsAllowed, Map<Long, Integer> ingredientMap) {
        int count = 0;
        List<CocktailIngredientRelationship> relationships = this.userRepo.getCocktailRelationships(cocktailId);
        for(int j = 0, p = relationships.size(); j < p; j++) {
            CocktailIngredientRelationship relationship = relationships.get(j);
            Long ingredientId = relationship.getIngredientId();
            if(!ingredientMap.containsKey(ingredientId)) {
                count += 1;
                if (count > missingIngredientsAllowed) {
                    return false;
                }
            }
        }
        return true;
    }

}

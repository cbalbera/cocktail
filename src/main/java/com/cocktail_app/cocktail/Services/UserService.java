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
        UserDB user = userRepo.findUserByUUID(id);
        if (user != null) {
            return convertUserDBToUserDTO(user);
        } else {
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
        System.out.println("finding user by email address of "+user.getEmail());
        System.out.println("value of exists function is "+userRepo.existsByEmail(user.getEmail()));
        if (!userRepo.existsByEmail(user.getEmail())) {
            addUser(user);
            return 1;
        } else {
            return 0;
        }
    }

    public CocktailDB findCocktailByID(Long cocktailId) {
        return this.userRepo.findCocktailByID(cocktailId);
    }

    public List<CocktailDTO> getUserCocktails(UUID userId) {
        UserDTO user = findUserById(userId);
        List<CocktailDTO> output = new ArrayList<>();
        ListIterator<Long> iterator = user.getCocktailList().listIterator();
        while(iterator.hasNext()) {
            CocktailDB cocktailDB = findCocktailByID(iterator.next());
            CocktailDTO cocktailDTO = converter.convertCocktailDBToCocktailDTO(cocktailDB);
            output.add(cocktailDTO);
        }
        return output;
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
        String cocktailList = listLongToString(user.getCocktailList());
        String pantry = listLongToString(user.getPantry());
        String favoriteCocktails = listLongToString(user.getFavoriteCocktails());
        String favoriteBartenders = listLongToString(user.getFavoriteBartenders());
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

    //TODO
    // login function
    // receive password from user as a put request
    // hash password
    // check for match

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
    private String listLongToString(List<Long> listLong) {
        if (listLong == null) { return null; }
        String output = listLong
                .stream()
                .map(String::valueOf)
                .collect(Collectors.joining("~"));
        return output;
    }


}

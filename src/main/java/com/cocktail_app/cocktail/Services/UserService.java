package com.cocktail_app.cocktail.Services;

import com.cocktail_app.cocktail.Helpers.CocktailConverter;
import com.cocktail_app.cocktail.Models.*;
import com.cocktail_app.cocktail.Repositories.UserRepo;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import java.util.*;

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
            // sort user's pantry - this will save time in future calls
            // specifically within updatePantry
            List<Long> pantry = converter.parseStringToListLong(user.getPantry());
            Collections.sort(pantry);
            user.setPantry(converter.listLongToString(pantry));
            // add user
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

    public List<IngredientDTO> updatePantry(UUID userId, List<Long> newPantry) {
        // newPantry will be given in the form of a list of IDs in form Long
        // that represent IngredientDTO.
        // IDs with negative values will be those whose presence in the pantry
        // have been **changed** - that includes addition or deletion
        // the list will be sorted, which will float all negative IDs to the
        // front; this will enable us to search for only those in a copy of the
        // user's existing pantry and either add it (if it doesn't exist) or
        // delete it if it does.  for each item, we can pop off the ID from the
        // existing pantry list if it already exists to shorten lookup time of
        // future items; we will also keep track of index in existing pantry to
        // further shorten lookup times and ensure we only need one nested loop
        // to accomplish this update
        // TODO handle case where pantry is null
        // TODO check that each ingredient id exists in db
        UserDB user = userRepo.findUserByUUID(userId);
        List<Long> userPantryCopy = converter.parseStringToListLong(user.getPantry());
        System.out.println("userPantryCopy is "+userPantryCopy);
        System.out.println("newPantry is "+newPantry);
        Collections.sort(newPantry); // TODO confirm this works
        System.out.println("newPantry is now "+newPantry);
        // user pantries are already sorted ascending in addUser
        int i = 0, j = userPantryCopy.size()-1;
        while(newPantry.get(i) < 0) {
            Long idValue = -1*newPantry.get(i);
            System.out.println("evaluating i of "+i+", idValue of "+idValue);
            while (j > 0 && idValue < userPantryCopy.get(j)) {
                // skip this element by decrementing j
                System.out.println("skipping index of "+j+" with id of "+userPantryCopy.get(j));
                j--;
            }
            // if, after skipping all items greater than current idValue
            // we find a match --> change is a delete
            System.out.println("found match at index of "+j);
            if (idValue == userPantryCopy.get(j)) {
                System.out.println("deleting at index of "+j+" to remove id of "+userPantryCopy.get(j));
                // delete item
                userPantryCopy.remove(j);
                j = Math.max(j-1,0);
                System.out.println("j is now "+j);
            } else {
                System.out.println("adding id of "+idValue+" at index of "+(j+1));
                // this item did not already exist in pantry -->
                // change is an add
                userPantryCopy.add(j+1,idValue);
            }
            if (j == -1) {
                // add all remaining changed ingredients - all ingredients
                // with negative values
                while (newPantry.get(i) < 0) {
                    idValue = -1*newPantry.get(i);
                    userPantryCopy.add(0,idValue);
                    i++;
                }

                // break out of loop
                break;
            }
            // increment i to search for next changed ingredient ID
            i++;
        }
        // here, we could do nothing & simply persist the new pantry to the
        // database via conversion of List<Long> to String

        //TODO test me
        String userNewPantry = converter.listLongToString(userPantryCopy);
        System.out.println("newPantry is "+userNewPantry);
        userRepo.updatePantry(userNewPantry,userId);

        // I am also going to have this output List<IngredientDTO> using this
        // last loop because I expect we will want to show the user their new
        // pantry once it has been updated

        // there is also the potential to have this run faster by doing the
        // conversion within the nested while loop above
        //TODO these next five lines should probably be a helper function
        List<IngredientDTO> output = new ArrayList<>();
        for (Long k:userPantryCopy) {
            IngredientDB ingredientDB = userRepo.findIngredientById(k);
            output.add(converter.convertIngredientDBToIngredientDTO(ingredientDB));
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

package com.cocktail_app.cocktail.Controllers;

import com.cocktail_app.cocktail.Models.*;
import com.cocktail_app.cocktail.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path="api/v1/user/") //TODO: control access appropriately with token to ensure only appropriate user can access
// this class will include all endpoints that will be used by the application to get user-related data
public class UserControllerExternal {

    private final UserService userService;

    @Autowired
    public UserControllerExternal(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login/attempt")
    public ResponseEntity<Boolean> loginAttempt(@RequestBody Map<String,String> json) {
        String email = json.get("email");
        String rawPassword = json.get("rawPassword");
        Boolean loginSuccess = userService.userLogIn(email,rawPassword);
        if (loginSuccess) {
            System.out.println("login success");
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<UserDB> createUser(@RequestBody UserDB user) {
        short creationSuccess = userService.createUser(user);
        if (creationSuccess == 1) {
            return new ResponseEntity<>(user,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
        }
    }
    // TODO: implement OAuth2.0 on the below endpoints after successful login

    // for the below two endpoints to work, JSON request body must include only the UUID
    // wrapped neither in [] brackets NOR enclosing {} braces
    @PostMapping("/profile/")
    public UserDTO getUser(@RequestBody UUID userId) { return this.userService.findUserById(userId); }

    @PostMapping("/profile/cocktails/")
    public List<CocktailDTO> getUserCocktails(@RequestBody UUID userId) {
        return this.userService.getUserCocktails(userId);
    }

    /*
    @PostMapping("/profile/makeablecocktails")
    public List<CocktailDTO> GetMakeableCocktails(@RequestBody UUID userId) {
        return this.userService.GetMakeableCocktails(userId);
    }

    @PostMapping("/profile/almostmakeablecocktails")
    public List<CocktailDTO> GetAlmostMakeableCocktails(@RequestBody UUID userId) {
        return this.userService.GetAlmostMakeableCocktails(userId);
    }

     */


    @PostMapping("/startup/cocktails")
    public List<CocktailDTO> GetCocktailsOnStartup(@RequestBody UUID userId) {
        return  this.userService.getAllCocktailsByUser(userId);
    }

    // front end will handle sending new pantry list:
    @PutMapping("/profile/updatepantry/{userId}")
    public List<IngredientDTO> updatePantry(@RequestBody List<Long> newPantry, @PathVariable UUID userId) {
        // could modify the next two lines so that they are handled by a helper within userService
        List<HashSet<Long>> modifiedList = this.userService.updatePantry(userId,newPantry);
        // TODO: figure out how to run these two concurrently
        //this.userService.updateAllCocktailsByUserUponChange(userId,modifiedList);
        return this.userService.getPantry(userId);

    }

    // TODO: add getPantry?

}

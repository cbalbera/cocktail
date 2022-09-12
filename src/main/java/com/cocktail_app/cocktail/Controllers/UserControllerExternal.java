package com.cocktail_app.cocktail.Controllers;

import com.cocktail_app.cocktail.Models.CocktailDB;
import com.cocktail_app.cocktail.Models.CocktailDTO;
import com.cocktail_app.cocktail.Models.UserDB;
import com.cocktail_app.cocktail.Models.UserDTO;
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

    // for user data - frontend will likely only access the endpoints it needs, but
    // will it be necessary to enforce this?  e.g. the UserDTO returned
    @PostMapping("/profile")
    public UserDTO getUser(@RequestBody UUID userId) { return this.userService.findUserById(userId); }

    @PostMapping("/login/attempt")
    public ResponseEntity<Boolean> loginAttempt(@RequestBody Map<String,String> json) {
        String email = json.get("email");
        String rawPassword = json.get("rawPassword");
        Boolean loginSuccess = userService.userLogIn(email,rawPassword);
        if (loginSuccess) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Boolean> createUser(@RequestBody UserDB user) {
        short creationSuccess = userService.createUser(user);
        if (creationSuccess == 1) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/profile/cocktails")
    public List<CocktailDTO> getUserCocktails(@RequestBody UUID userId) {
        return userService.getUserCocktails(userId);
    }

}

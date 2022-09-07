package com.cocktail_app.cocktail.Controllers;

import com.cocktail_app.cocktail.Models.CocktailDB;
import com.cocktail_app.cocktail.Models.UserDB;
import com.cocktail_app.cocktail.Models.UserDTO;
import com.cocktail_app.cocktail.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/v1/user/admin") //TODO: for now this will be viewable; before go live, control access appropriately with token
// this class will include all endpoints that will be used by the application to get user-related data
public class UserControllerInternal {

    private final UserService userService;

    @Autowired
    public UserControllerInternal(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public List<UserDTO> getUsers() { return this.userService.getUsers(); }

    // TODO: figure out where this method goes - and potential alternate implementation, esp. w/r/t intake and hashing of pwd for storage - for a new user to create themself
    @PostMapping("/add")
    public ResponseEntity<UserDB> addUser(@RequestBody UserDTO user) {
        UserDB newUser = this.userService.addUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<UserDB> updateUser(@PathVariable Long userId, @RequestBody UserDTO user) {
        if(true) { //TODO: here, check if id is in db
            UserDB newUser = this.userService.addUser(user);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

}

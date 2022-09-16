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
import java.util.UUID;

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

    @PostMapping("/add")
    public ResponseEntity<UserDB> addUser(@RequestBody UserDB user) {
        short creationSuccess = userService.createUser(user);
        if (creationSuccess == 1) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<UserDB> updateUser(@PathVariable UUID userId, @RequestBody UserDB user) {
        if(userService.existsById(userId)) {
            UserDB newUser = this.userService.addUser(user);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable UUID userId) {
        if(userService.existsById(userId)) {
            this.userService.deleteUser(userId);
            return new ResponseEntity<>(true,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
        }
    }

}

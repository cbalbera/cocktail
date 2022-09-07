package com.cocktail_app.cocktail.Controllers;

import com.cocktail_app.cocktail.Models.UserDB;
import com.cocktail_app.cocktail.Models.UserDTO;
import com.cocktail_app.cocktail.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/{userId}")
    public UserDTO getUser(@PathVariable Long userId) { return this.userService.findUserById(userId); }


}

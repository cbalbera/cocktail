package com.cocktail_app.cocktail.Controllers;

import com.cocktail_app.cocktail.Models.UserDTO;
import com.cocktail_app.cocktail.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}

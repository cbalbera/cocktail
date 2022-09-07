package com.cocktail_app.cocktail.Models;

import java.beans.Transient;
import java.util.List;
import java.util.UUID;

public class UserDTO {
    public enum userType{
        USER,
        BARTENDER,
        ADMIN
    }
    private UUID id;
    private String username;
    private String hashedPassword;
    private userType userType; // enumerated
    private List<Long> cocktailList;
    // TODO: in pantry, do we want to account for quantity of each item?
    // see reasoning in UserDB.java class - currently assuming no
    // as such, pantry in UserDTO is a list of ingredient IDs of type Long
    private List<Long> pantry;
    private List<Long> favoriteCocktails;
    private List<Long> favoriteBartenders;

    // empty constructor
    public UserDTO() {
    }

    // constructor with all items
    public UserDTO(UUID id, String username, String hashedPassword, userType userType, List<Long> cocktailList, List<Long> pantry, List<Long> favoriteCocktails, List<Long> favoriteBartenders) {
        this.id = id;
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.userType = userType;
        this.cocktailList = cocktailList;
        this.pantry = pantry;
        this.favoriteCocktails = favoriteCocktails;
        this.favoriteBartenders = favoriteBartenders;
    }

    // constructor with all items except for auto-generated ID
    public UserDTO(String username, String hashedPassword, userType userType, List<Long> cocktailList, List<Long> pantry, List<Long> favoriteCocktails, List<Long> favoriteBartenders) {
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.userType = userType;
        this.cocktailList = cocktailList;
        this.pantry = pantry;
        this.favoriteCocktails = favoriteCocktails;
        this.favoriteBartenders = favoriteBartenders;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public userType getUserType() {
        return userType;
    }

    public List<Long> getCocktailList() {
        return cocktailList;
    }

    public List<Long> getPantry() {
        return pantry;
    }

    public List<Long> getFavoriteCocktails() {
        return favoriteCocktails;
    }

    public Argon2PasswordEncoder getEncoder() {
        // the parameters of the below call should be stored somewhere safely (i.e. a file not on Github) going fwd
        return new Argon2PasswordEncoder(16,32,1,1<<12,3);
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<Long> getFavoriteBartenders() {
        return favoriteBartenders;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setHashedPassword(String password) {
        this.hashedPassword = this.getEncoder().encode(password);
    }

    public void setUserType(userType userType) {
        this.userType = userType;
    }

    public void setCocktailList(List<Long> cocktailList) {
        this.cocktailList = cocktailList;
    }
    public void setPantry(List<Long> pantry) {
        this.pantry = pantry;
    }

    public void setFavoriteCocktails(List<Long> favoriteCocktails) {
        this.favoriteCocktails = favoriteCocktails;
    }

    public void setFavoriteBartenders(List<Long> favoriteBartenders) {
        this.favoriteBartenders = favoriteBartenders;
    }

    // excludes hashed password
    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", userType=" + userType +
                ", cocktailList='" + cocktailList + '\'' +
                ", pantry='" + pantry + '\'' +
                ", favoriteCocktails='" + favoriteCocktails + '\'' +
                ", favoriteBartenders='" + favoriteBartenders + '\'' +
                '}';
    }
}

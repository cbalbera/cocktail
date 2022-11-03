package com.cocktail_app.cocktail.Models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.beans.Transient;
import java.util.List;
import java.util.UUID;

public class UserDTO {

    public enum userType{
        USER,
        BARTENDER,
        ADMIN
    }
    private UUID userId;
    private String firstName;
    private String lastName;
    private String email;
    private String hashedPassword;
    private userType userType; // enumerated
    private List<Long> cocktailList;
    // moving fwd with assumption that pantry does not include quantities
    private List<Long> pantry;
    private List<Long> favoriteCocktails;
    private List<Long> favoriteBartenders;
    private int zipCode;
    private int numIngredientsInBar;

    // empty constructor
    public UserDTO() {
    }

    // constructor with all items

    public UserDTO(UUID id, String firstName, String lastName, String email, String hashedPassword, UserDTO.userType userType, List<Long> cocktailList, List<Long> pantry, List<Long> favoriteCocktails, List<Long> favoriteBartenders, int zipCode) {
        this.userId = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.userType = userType;
        this.cocktailList = cocktailList;
        this.pantry = pantry;
        this.favoriteCocktails = favoriteCocktails;
        this.favoriteBartenders = favoriteBartenders;
        this.zipCode = zipCode;
    }

    // constructor with all items except for auto-generated ID

    public UserDTO(String firstName, String lastName, String email, String hashedPassword, UserDTO.userType userType, List<Long> cocktailList, List<Long> pantry, List<Long> favoriteCocktails, List<Long> favoriteBartenders, int zipCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.userType = userType;
        this.cocktailList = cocktailList;
        this.pantry = pantry;
        this.favoriteCocktails = favoriteCocktails;
        this.favoriteBartenders = favoriteBartenders;
        this.zipCode = zipCode;
    }

    public UUID getId() {
        return userId;
    }

    public void setId(UUID id) {
        this.userId = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }
    public void setHashedPassword(String password) {
        Argon2PasswordEncoder encoder = new Argon2PasswordEncoder();
        this.hashedPassword = encoder.encode(password);
    }

    public UserDTO.userType getUserType() {
        return userType;
    }

    public void setUserType(UserDTO.userType userType) {
        this.userType = userType;
    }

    public List<Long> getCocktailList() {
        return cocktailList;
    }

    public void setCocktailList(List<Long> cocktailList) {
        this.cocktailList = cocktailList;
    }

    public List<Long> getPantry() {
        return pantry;
    }

    public void setPantry(List<Long> pantry) {
        this.pantry = pantry;
    }

    public List<Long> getFavoriteCocktails() {
        return favoriteCocktails;
    }

    public void setFavoriteCocktails(List<Long> favoriteCocktails) {
        this.favoriteCocktails = favoriteCocktails;
    }

    public List<Long> getFavoriteBartenders() {
        return favoriteBartenders;
    }

    public void setFavoriteBartenders(List<Long> favoriteBartenders) {
        this.favoriteBartenders = favoriteBartenders;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    // excludes hashed password
    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + userId +
                ", email='" + email + '\'' +
                ", userType=" + userType +
                ", cocktailList='" + cocktailList + '\'' +
                ", pantry='" + pantry + '\'' +
                ", favoriteCocktails='" + favoriteCocktails + '\'' +
                ", favoriteBartenders='" + favoriteBartenders + '\'' +
                '}';
    }
}

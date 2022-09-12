package com.cocktail_app.cocktail.Models;

import javax.persistence.*;
import java.util.UUID;

@Entity(name="UserDB")
@Table(name="Users")
public class UserDB {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="user_id", insertable = false, updatable = false, nullable = false)
    // insertable, updatable, nullable can also be set as such for other DB objects
    // if we deem this necessary - can see potential reasons why we would do this
    // (e.g. the use of IDs to x-ref from user > pantry and user > cocktail list)
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String hashedPassword;
    private int userType; // enumerated
    private String cocktailList;
    // TODO: in pantry, do we want to account for quantity of each item?
    // current thought is no due to scope - we're not currently building a pantry app
    // but may frustrate users if we say they can make something and they actually can't
    // given that we have the information needed (e.g. quantity of each ingredient needed
    // to make each drink) to make this a relatively trivial calculation should we choose
    // to go this way
    private String pantry;
    private String favoriteCocktails;
    private String favoriteBartenders;
    private int zipCode;

    // empty constructor
    public UserDB() {
    }

    // constructor with all items

    public UserDB(UUID id, String firstName, String lastName, String email, String hashedPassword, int userType, String cocktailList, String pantry, String favoriteCocktails, String favoriteBartenders, int zipCode) {
        this.id = id;
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


    public UserDB(String firstName, String lastName, String email, String hashedPassword, int userType, String cocktailList, String pantry, String favoriteCocktails, String favoriteBartenders, int zipCode) {
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
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String password) {
        Argon2PasswordEncoder encoder = new Argon2PasswordEncoder();
        this.hashedPassword = encoder.encode(password);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getCocktailList() {
        return cocktailList;
    }

    public void setCocktailList(String cocktailList) {
        this.cocktailList = cocktailList;
    }

    public String getPantry() {
        return pantry;
    }

    public void setPantry(String pantry) {
        this.pantry = pantry;
    }

    public String getFavoriteCocktails() {
        return favoriteCocktails;
    }

    public void setFavoriteCocktails(String favoriteCocktails) {
        this.favoriteCocktails = favoriteCocktails;
    }

    public String getFavoriteBartenders() {
        return favoriteBartenders;
    }

    public void setFavoriteBartenders(String favoriteBartenders) {
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
        return "UserDB{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", userType=" + userType +
                ", cocktailList='" + cocktailList + '\'' +
                ", pantry='" + pantry + '\'' +
                ", favoriteCocktails='" + favoriteCocktails + '\'' +
                ", favoriteBartenders='" + favoriteBartenders + '\'' +
                '}';
    }
}

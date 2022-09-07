package com.cocktail_app.cocktail.Models;

import javax.persistence.*;
import java.util.UUID;

@Entity(name="UserDB")
@Table(name="users")
public class UserDB {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="user_id", insertable = false, updatable = false, nullable = false)
    // insertable, updatable, nullable can also be set as such for other DB objects
    // if we deem this necessary - can see potential reasons why we would do this
    // (e.g. the use of IDs to x-ref from user > pantry and user > cocktail list)
    private UUID id;
    private String username;
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

    // empty constructor
    public UserDB() {
    }

    // constructor with all items
    public UserDB(UUID id, String username, String hashedPassword, int userType, String cocktailList, String pantry, String favoriteCocktails, String favoriteBartenders) {
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
    public UserDB(String username, String hashedPassword, int userType, String cocktailList, String pantry, String favoriteCocktails, String favoriteBartenders) {
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

    public int getUserType() {
        return userType;
    }

    public String getCocktailList() {
        return cocktailList;
    }

    public String getPantry() {
        return pantry;
    }

    public String getFavoriteCocktails() {
        return favoriteCocktails;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFavoriteBartenders() {
        return favoriteBartenders;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    //TODO: overwrite this with setter that uses pwd encoder
    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public void setCocktailList(String cocktailList) {
        this.cocktailList = cocktailList;
    }
    public void setPantry(String pantry) {
        this.pantry = pantry;
    }

    public void setFavoriteCocktails(String favoriteCocktails) {
        this.favoriteCocktails = favoriteCocktails;
    }

    public void setFavoriteBartenders(String favoriteBartenders) {
        this.favoriteBartenders = favoriteBartenders;
    }

    // excludes hashed password
    @Override
    public String toString() {
        return "UserDB{" +
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

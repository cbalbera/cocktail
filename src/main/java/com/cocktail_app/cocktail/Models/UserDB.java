package com.cocktail_app.cocktail.Models;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity(name="UserDB")
@Table(name="Users")
public class UserDB {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="user_id", insertable = false, updatable = false, nullable = false)
    // apparently, @Type is deprecated with no replacement & was reinstated in Hibernate 6.0.  suppressing/ignoring
    // this warning.  https://stackoverflow.com/questions/69858533/replacement-for-hibernates-deprecated-type-annotation
    @Type(type="org.hibernate.type.UUIDCharType")
    // insertable, updatable, nullable can also be set as such for other DB objects
    // if we deem this necessary - can see potential reasons why we would do this
    // (e.g. the use of IDs to x-ref from user > pantry and user > cocktail list)
    private UUID userId;
    private String firstName;
    private String lastName;
    private String email;
    private String hashedPassword;
    private int userType; // enumerated
    private String cocktailList;
    // moving fwd with assumption that pantry does not include quantities
    private String pantry;
    private String favoriteCocktails;
    private String favoriteBartenders;
    private int zipCode;
    private String makeableCocktails;

    // empty constructor
    public UserDB() {
    }

    // constructor with all items

    public UserDB(UUID id, String firstName, String lastName, String email, String hashedPassword, int userType, String cocktailList, String pantry, String favoriteCocktails, String favoriteBartenders, int zipCode, String makeableCocktails) {
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
        this.makeableCocktails = makeableCocktails;
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

    public String getMakeableCocktails() {
        return makeableCocktails;
    }

    public void setMakeableCocktails(String makeableCocktails) {
        this.makeableCocktails = makeableCocktails;
    }

    // excludes hashed password
    @Override
    public String toString() {
        return "UserDB{" +
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

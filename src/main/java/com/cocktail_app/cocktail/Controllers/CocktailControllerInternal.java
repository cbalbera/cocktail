package com.cocktail_app.cocktail.Controllers;

import com.cocktail_app.cocktail.Models.CocktailDB;
import com.cocktail_app.cocktail.Models.CocktailDTO;
import com.cocktail_app.cocktail.Services.CocktailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/v1/Cocktail/admin") //TODO require specific auth to visit admin endpoints
public class CocktailControllerInternal {

    private final CocktailService cocktailService;

    @Autowired
    public CocktailControllerInternal(CocktailService cocktailService) {
        this.cocktailService = cocktailService;
    }

    @PostMapping("/add")
    public ResponseEntity<CocktailDB> addCocktail(@RequestBody CocktailDB cocktail) {
        CocktailDB newCocktail = this.cocktailService.addCocktail(cocktail);
        return new ResponseEntity<>(newCocktail, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CocktailDB> updateCocktail(@PathVariable Long id, @RequestBody CocktailDTO cocktail) {
        if(true) { //TODO: here, check if id is in db
            CocktailDB newCocktail = this.cocktailService.updateCocktail(cocktail);
            return new ResponseEntity<>(newCocktail, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteCocktail(@PathVariable Long id) {
        if(true) { //TODO: here, check if id is in db
            this.cocktailService.deleteCocktail(id);
            return new ResponseEntity<>(true,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/addChildren/{cocktailId}")
    public ResponseEntity<CocktailDB> addChildren(@PathVariable Long cocktailId, @RequestBody List<Long> childrenIDs) {
        if(true) { //TODO: here, check if id is in db
            CocktailDB newCocktail = this.cocktailService.addChildren(cocktailId,childrenIDs);
            return new ResponseEntity<>(newCocktail, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/addParent/{cocktailId}")
    public ResponseEntity<CocktailDB> addParent(@PathVariable Long cocktailId, @RequestBody Long parentId) {
        if(true) { //TODO: here, check if id is in db
            CocktailDB newCocktail = this.cocktailService.addParent(cocktailId,parentId);
            return new ResponseEntity<>(newCocktail, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }
}

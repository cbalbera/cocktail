package com.cocktail_app.cocktail.Controllers;

import com.cocktail_app.cocktail.Models.CocktailDB;
import com.cocktail_app.cocktail.Models.CocktailDTO;
import com.cocktail_app.cocktail.Services.CocktailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="api/v1/Cocktail/admin") //TODO require specific auth to visit admin endpoints
public class CocktailControllerInternal {

    private final CocktailService cocktailService;

    @Autowired
    public CocktailControllerInternal(CocktailService cocktailService) {
        this.cocktailService = cocktailService;
    }

    @PostMapping("/add")
    public ResponseEntity<CocktailDB> addCocktail(@RequestBody CocktailDTO cocktail) {
        CocktailDB newCocktail = this.cocktailService.addCocktail(cocktail);
        return new ResponseEntity<>(newCocktail, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<CocktailDB> updateCocktail(@RequestBody CocktailDTO cocktail) {
        CocktailDB newCocktail = this.cocktailService.updateCocktail(cocktail);
        return new ResponseEntity<>(newCocktail,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteCocktail(@PathVariable Long id) {
        if(true) { // here, check if id is in db
            this.cocktailService.deleteCocktail(id);
            return new ResponseEntity<>(true,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
        }
    }
}

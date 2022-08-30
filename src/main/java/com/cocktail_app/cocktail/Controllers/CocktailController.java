package com.cocktail_app.cocktail.Controllers;

import com.cocktail_app.cocktail.Models.Cocktail;
import com.cocktail_app.cocktail.Models.CocktailDB;
import com.cocktail_app.cocktail.Services.CocktailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path="api/v1/Cocktail") //TODO update
public class CocktailController {

    private final CocktailService cocktailService;

    @Autowired
    public CocktailController(CocktailService cocktailService) {
        this.cocktailService = cocktailService;
    }

    @GetMapping("all")
    public List<Cocktail> getCocktails() {
        return this.cocktailService.getCocktails();
    }

    @GetMapping("/result/{id}")
    public Cocktail getCocktailByID(Long id) {
        return this.cocktailService.findCocktailById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<CocktailDB> addCocktail(Cocktail cocktail) {
        CocktailDB newCocktail = this.cocktailService.addCocktail(cocktail);
        return new ResponseEntity<>(newCocktail,HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<CocktailDB> updateCocktail(Cocktail cocktail) {
        CocktailDB newCocktail = this.cocktailService.updateCocktail(cocktail);
        return new ResponseEntity<>(newCocktail,HttpStatus.OK);
    }
}

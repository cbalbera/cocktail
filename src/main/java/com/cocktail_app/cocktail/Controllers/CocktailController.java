package com.cocktail_app.cocktail.Controllers;

import com.cocktail_app.cocktail.Models.CocktailDTO;
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
    public List<CocktailDTO> getCocktails() {
        return this.cocktailService.getCocktails();
    }

    @GetMapping("/result/{id}")
    public CocktailDTO getCocktailByID(@PathVariable Long id) {
        return this.cocktailService.findCocktailById(id);
    }

    //TODO
    //public List<Cocktail> GetMakeableCocktails
    // this will likely have to be called strategically rather than upon page open
    // in order to minimize loading time (perhaps cron only every [X] minutes?)
    // because time complexity is O(NK) where N = # cocktails and K = # ingredients in pantry

    //TODO
    //public List<Cocktail> GetAlmostMakeableCocktails
    // this will likely have to be called strategically rather than upon page open
    // in order to minimize loading time (perhaps cron only every [X] minutes?)
    // especially so here, because time complexity is O(NK) * P where P is the
    // number of params we are going to search on here
}

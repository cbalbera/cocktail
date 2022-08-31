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

    // eventually, this will be moved to a different (internal- or bartender-facing) controller
    @PostMapping("/add")
    public ResponseEntity<CocktailDB> addCocktail(@RequestBody CocktailDTO cocktail) {
        CocktailDB newCocktail = this.cocktailService.addCocktail(cocktail);
        return new ResponseEntity<>(newCocktail,HttpStatus.CREATED);
    }

    // eventually, this will be moved to a different (internal- or bartender-facing) controller
    @PutMapping("/update")
    public ResponseEntity<CocktailDB> updateCocktail(@RequestBody CocktailDTO cocktail) {
        CocktailDB newCocktail = this.cocktailService.updateCocktail(cocktail);
        return new ResponseEntity<>(newCocktail,HttpStatus.OK);
    }

    // very dangerous
    // if using this store pwd in a protected file
    // and/or just delete this function once any real data have been entered
    /*
    @DeleteMapping("/deleteAll/{password}")
    public ResponseEntity<Boolean> clearCocktails(@PathVariable String password) {
        if(password == "[password]") {
            this.cocktailService.deleteCocktails();
            return new ResponseEntity<>(true,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
        }
    }

    */

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

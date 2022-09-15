package com.cocktail_app.cocktail.Controllers;

import com.cocktail_app.cocktail.Models.CocktailDTO;
import com.cocktail_app.cocktail.Services.CocktailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path="api/v1/Cocktail") //TODO update
public class CocktailControllerExternal {

    private final CocktailService cocktailService;

    @Autowired
    public CocktailControllerExternal(CocktailService cocktailService) {
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
}

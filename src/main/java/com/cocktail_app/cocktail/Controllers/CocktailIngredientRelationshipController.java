package com.cocktail_app.cocktail.Controllers;

import com.cocktail_app.cocktail.Models.CocktailDB;
import com.cocktail_app.cocktail.Models.CocktailIngredientRelationship;
import com.cocktail_app.cocktail.Services.CocktailIngredientRelationshipService;
import com.cocktail_app.cocktail.Services.CocktailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/v1/Cocktail/IngredientRelationship")
public class CocktailIngredientRelationshipController {

    private final CocktailIngredientRelationshipService relationshipService;

    @Autowired
    public CocktailIngredientRelationshipController(CocktailIngredientRelationshipService relationshipService) {
        this.relationshipService = relationshipService;
    }

    @PostMapping("/add")
    public ResponseEntity<CocktailIngredientRelationship> addRelationship(@RequestBody CocktailIngredientRelationship relationship) {
        CocktailIngredientRelationship newRelationship = this.relationshipService.addRelationshipObject(relationship);
        return new ResponseEntity<>(newRelationship, HttpStatus.CREATED);
    }

    // for this endpoint to work, JSON request body must include only the list of IDs in [] brackets - no enclosing {} braces
    @PostMapping("/addTo/{cocktailId}")
    public ResponseEntity<Boolean> addIngredientsToOneCocktail(@RequestBody List<Long> ingredientIds, @PathVariable Long cocktailId) {
        this.relationshipService.addIngredientsToOneCocktail(cocktailId,ingredientIds);
        return new ResponseEntity<>(true, HttpStatus.CREATED);
    }
}

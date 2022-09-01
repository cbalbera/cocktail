package com.cocktail_app.cocktail.Controllers;

import com.cocktail_app.cocktail.Models.IngredientDB;
import com.cocktail_app.cocktail.Models.IngredientDTO;
import com.cocktail_app.cocktail.Services.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/v1/Cocktail/IngredientAdmin")
public class IngredientController {

    // this entire controller is internal-facing
    private final IngredientService ingredientService;

    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("all")
    public List<IngredientDTO> getIngredients() {
        return this.ingredientService.getIngredients();
    }

    @GetMapping("/result/{id}")
    public IngredientDTO getIngredient(Long id) {
        return this.ingredientService.findIngredientById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<IngredientDB> addIngredient(@RequestBody IngredientDB ingredient) {
        IngredientDB newIngredient = this.ingredientService.addIngredient(ingredient);
        return new ResponseEntity<>(newIngredient,HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteIngredient(@PathVariable Long id) {
        if(true) { //TODO: here, check if id is in db
            this.ingredientService.deleteIngredient(id);
            return new ResponseEntity<>(true,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
        }
    }
}

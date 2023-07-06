//package com.cocktail_app.cocktail.ServiceTests;
//
//import com.cocktail_app.cocktail.Models.CocktailDB;
//import com.cocktail_app.cocktail.Services.CocktailService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class CocktailServiceTest {
//    private final CocktailService cocktailService;
//
//    @Autowired
//    public CocktailServiceTest(CocktailService cocktailService) {
//        this.cocktailService = cocktailService;
//    }
//
//    @Test
//    public void testAddCocktail() {
//        // ideally, we would do this testing on a copy of the DB rather than the DB itself to avoid potential changes to real-life data
//
//        // general test of functionality
//        CocktailDB cocktail1 = new CocktailDB(69696969L,"test1","bottle",0,"1.pour drink,2.drink drink","test","glass","none",false,null,false,null,null,"","1",1);
//        CocktailDB cocktail2 = this.cocktailService.addCocktail(cocktail1);
//        assertEquals(cocktail2.getName(),"test1");
//        assertEquals(cocktail2.getTools(),"bottle");
//        assertEquals(cocktail2.getDifficulty(),0);
//        assertEquals(cocktail2.getInstructions(),"1.pour drink~2.drink drink");
//
//        this.cocktailService.deleteCocktail(69696969L);
//    }
//}

package com.FoodGuy.controller;

import com.FoodGuy.Model.Food;
import com.FoodGuy.Model.Restaurant;
import com.FoodGuy.Model.User;
import com.FoodGuy.Request.CreateFoodRequest;
import com.FoodGuy.Service.FoodService;
import com.FoodGuy.Service.RestaurantService;
import com.FoodGuy.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {


    private final FoodService foodService;

    private final UserService userService;

    private final RestaurantService restaurantService;

    public FoodController(FoodService foodService, UserService userService, RestaurantService restaurantService) {
        this.foodService = foodService;
        this.userService = userService;
        this.restaurantService = restaurantService;
    }


    @GetMapping("/SearchFood")
    public ResponseEntity<List<Food>> searchFood(@RequestParam String foodName,
                                           @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Food> foods = foodService.SearchFood(foodName);
        return new ResponseEntity<>(foods, HttpStatus.OK);

    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> getRestaurantFood (@RequestParam boolean vegeterian,
                                                         @RequestParam boolean seasonal,
                                                         @RequestParam boolean nonVeg,
                                                         @RequestParam(required = false) String food_Category,
                                                         @PathVariable Long restaurantId,
                                                 @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Food> foods = foodService.getRestaurantFood(restaurantId,vegeterian,nonVeg,seasonal,food_Category);
        return new ResponseEntity<>(foods, HttpStatus.OK);

    }




}

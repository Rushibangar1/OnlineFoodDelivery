package com.FoodGuy.controller;


import com.FoodGuy.Dto.RestaurantDto;
import com.FoodGuy.Logger.BaseLogger;
import com.FoodGuy.Model.Restaurant;
import com.FoodGuy.Model.User;
import com.FoodGuy.Service.RestaurantService;
import com.FoodGuy.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController extends BaseLogger {



    private final RestaurantService restaurantService;
    private final UserService userService;



    public RestaurantController(RestaurantService restaurantService, UserService userService) {
        this.restaurantService = restaurantService;
        this.userService = userService;
    }

    // Search restaurants by keyword
    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> SearchRestaurant (
            @RequestHeader("Authorization") String jwt,
            @RequestParam String keyword
    ) throws Exception{
        logger.info("Searching restaurants with keyword: {}",keyword);
        User user = userService.findUserByJwtToken(jwt);
        List<Restaurant>  restaurantList = restaurantService.searchRestaurant(keyword);
        return ResponseEntity.ok(restaurantList);
    }

    // Get all restaurants
    @GetMapping("/all")
    public ResponseEntity<List<Restaurant>> GetAllRestaurant (
            @RequestHeader("Authorization") String jwt
    ) throws Exception{
        logger.info("fetching all the restaurants");
        List<Restaurant> restaurantList = restaurantService.getAllRestaurant();
        return ResponseEntity.ok(restaurantList);
    }

    // Get restaurant by ID
    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> findRestaurantById (
            @RequestHeader("Authorization") String jwt
            ,@PathVariable Long id
    ) throws Exception{
        logger.info("fetching restaurant with id: {}",id);
        Restaurant  restaurantList = restaurantService.findRestaurantById(id);
        return ResponseEntity.ok(restaurantList);
    }

    // Add a restaurant to favorites
    @PutMapping("/{id}/add-favorites")
    public ResponseEntity<RestaurantDto>  addToFavorites (
            @RequestHeader("Authorization") String jwt
            ,@PathVariable Long id
    ) throws Exception{
        logger.info("Adding restaurant with ID {} to favorites", id);
        User user = userService.findUserByJwtToken(jwt);
        RestaurantDto restaurant = restaurantService.addToFavorites(id,user);
        return ResponseEntity.ok(restaurant);
    }







}

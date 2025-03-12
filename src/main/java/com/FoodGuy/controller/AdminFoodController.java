package com.FoodGuy.controller;

import com.FoodGuy.Model.Food;
import com.FoodGuy.Model.Restaurant;
import com.FoodGuy.Model.User;
import com.FoodGuy.Request.CreateFoodRequest;
import com.FoodGuy.Service.FoodService;
import com.FoodGuy.Service.RestaurantService;
import com.FoodGuy.Service.UserService;
import com.FoodGuy.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {

    private final FoodService  foodService;

    private final UserService userService;

    private final RestaurantService restaurantService;


    public AdminFoodController(FoodService foodService, UserService userService, RestaurantService restaurantService) {
        this.foodService = foodService;
        this.userService = userService;
        this.restaurantService = restaurantService;
    }


    @PostMapping("/CreateFood")
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest request,
                                           @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.findRestaurantById(request.getRestuarantId());
        Food food = foodService.createFood(request,request.getCategory(),restaurant);

        return new ResponseEntity<>(food, HttpStatus.CREATED);

    }

    @DeleteMapping("/CreateFood")
    public ResponseEntity<MessageResponse> DeleteFood(@PathVariable Long id,
                                           @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        foodService.deletFood(id);

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Food Removed Succesfully");

        return new ResponseEntity<>(messageResponse, HttpStatus.CREATED);

    }

    @PutMapping("/CreateFood")
    public ResponseEntity<Food> UpdateFoodAvaibilityStatus(@PathVariable Long id,
                                                      @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        Food food = foodService.updateAvailabilityStatus(id);
        return new ResponseEntity<>(food, HttpStatus.CREATED);

    }






}

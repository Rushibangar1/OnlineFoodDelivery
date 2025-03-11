package com.FoodGuy.controller;

import com.FoodGuy.Model.Restaurant;
import com.FoodGuy.Model.User;
import com.FoodGuy.Request.CreateRestaurantRequest;
import com.FoodGuy.Service.RestaurantService;
import com.FoodGuy.Service.UserService;
import com.FoodGuy.response.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/restaurants")
public class AdminRestaurantController {




    private static final Logger logger = LoggerFactory.getLogger(AdminRestaurantController.class);


    @GetMapping("/SayHello")
    public String test(){
        logger.info("This controller method hit");
        return "This is a test";
    }


    private final RestaurantService restaurantService;
    private final UserService userService;

    public AdminRestaurantController(RestaurantService restaurantService, UserService userService) {
        this.restaurantService = restaurantService;
        this.userService = userService;
    }

    // Create a new restaurant (authentication required)
    @PostMapping("/createRest")
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody CreateRestaurantRequest request,
                                                       @RequestHeader("Authorization") String jwt) throws Exception {
        logger.info("Creating restaurant: {}", request.getName());
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.createRestaurant(request, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurant);
    }

    // Update an existing restaurant (authentication required)
    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@RequestBody CreateRestaurantRequest request,
                                                       @RequestHeader("Authorization") String jwt,
                                                       @PathVariable Long id) throws Exception {
        logger.info("Updating restaurant with ID: {}", id);
        userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.updateRestaurant(id, request);
        return ResponseEntity.ok(restaurant);
    }

    // Delete a restaurant (no authentication required)
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteRestaurant(@PathVariable Long id) throws Exception {
        logger.info("Deleting restaurant with ID: {}", id);
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.ok(new MessageResponse("Restaurant deleted successfully"));
    }

    // Update restaurant status (no authentication required)
    @PutMapping("/{id}/status")
    public ResponseEntity<Restaurant> updateRestaurantStatus(@PathVariable Long id) throws Exception {
        logger.info("Updating status for restaurant with ID: {}", id);
        Restaurant restaurant = restaurantService.updateRestaurantStatus(id);
        return ResponseEntity.ok(restaurant);
    }

    // Get the restaurant associated with the logged-in user (authentication required)
    @GetMapping("/user")
    public ResponseEntity<Restaurant> findRestaurantByUser(@RequestHeader("Authorization") String jwt) throws Exception {
        logger.info("Fetching restaurant for authenticated user");
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.getRestaurantByUserId(user.getId());
        return ResponseEntity.ok(restaurant);
    }
}

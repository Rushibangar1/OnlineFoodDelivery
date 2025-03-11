package com.FoodGuy.Service;

import com.FoodGuy.Dto.RestaurantDto;
import com.FoodGuy.Logger.BaseLogger;
import com.FoodGuy.Model.Address;
import com.FoodGuy.Model.Restaurant;
import com.FoodGuy.Model.User;
import com.FoodGuy.Repository.AddressRepository;
import com.FoodGuy.Repository.RestaurantRepository;
import com.FoodGuy.Repository.UserRepository;
import com.FoodGuy.Request.CreateRestaurantRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl extends BaseLogger implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, AddressRepository addressRepository, UserRepository userRepository) {
        this.restaurantRepository = restaurantRepository;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest request, User user) {
        logger.info("Creating restaurant for user: " + user.getId());
        Address address = addressRepository.save(request.getAddress());
        System.out.println(request);
        Restaurant restaurant = new Restaurant(
                address,
                request.getContactInformation(),
                request.getCuisineType(),
                request.getDescription(),
                request.getImages(),
                request.getName(),
                request.getOpeningHours(),
                LocalDateTime.now(),
                user
        );

        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        logger.info("Restaurant created with ID: " + savedRestaurant.getId());
        logger.warn("Restaurant name {}",savedRestaurant.getName());
        return savedRestaurant;
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updateRestaurant) throws Exception {
        logger.info("Updating restaurant with ID: " + restaurantId);
        Restaurant restaurant = findRestaurantById(restaurantId);

        if (updateRestaurant.getCuisineType() != null) {
            restaurant.setCuisineType(updateRestaurant.getCuisineType());
            logger.info("Cuisine type updated for restaurant ID: " + restaurantId);
        }
        if (updateRestaurant.getDescription() != null) {
            restaurant.setDescription(updateRestaurant.getDescription());
            logger.info("Description updated for restaurant ID: " + restaurantId);
        }
        if (updateRestaurant.getName() != null) {
            restaurant.setName(updateRestaurant.getName());
            logger.info("Name updated for restaurant ID: " + restaurantId);
        }

        Restaurant updatedRestaurant = restaurantRepository.save(restaurant);
        logger.info("Restaurant updated with ID: and name " + updatedRestaurant.getId());
        return updatedRestaurant;
    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {
        logger.info("Deleting restaurant with ID: " + restaurantId);
        Restaurant restaurant = findRestaurantById(restaurantId);
        restaurantRepository.delete(restaurant);
        logger.info("Restaurant with ID " + restaurantId + " deleted successfully.");
    }

    @Override
    public List<Restaurant> getAllRestaurant() {
        logger.info("Fetching all restaurants.");
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyword) {
        logger.info("Searching for restaurants with keyword: " + keyword);
        return restaurantRepository.findBySearchQuery(keyword);
    }

    @Override
    public Restaurant findRestaurantById(Long id) throws Exception {
        logger.info("Fetching restaurant with ID: " + id);
        Optional<Restaurant> opt = restaurantRepository.findById(id);
        if (opt.isEmpty()) {
            logger.error("Restaurant not found with ID: " + id);
            throw new Exception("Restaurant not found with ID: " + id);
        }
        logger.info("Restaurant found with ID: " + id);
        return opt.get();
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
        logger.info("Fetching restaurant for user ID: " + userId);
        Restaurant restaurant = restaurantRepository.findByOwnerId(userId);
        if (restaurant == null) {
            logger.error("Restaurant not found for owner ID: " + userId);
            throw new Exception("Restaurant not found with owner ID: " + userId);
        }
        logger.info("Restaurant found for owner ID: " + userId);
        return restaurant;
    }

    @Override
    public RestaurantDto addToFavorites(Long restaurantId, User user) throws Exception {
        logger.info("Adding restaurant with ID: " + restaurantId + " to user ID: " + user.getId() + "'s favorites.");
        Restaurant restaurant = findRestaurantById(restaurantId);

        // Check if the restaurant is already in favorites
        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setDescription(restaurant.getDescription());
        restaurantDto.setImages(restaurant.getImages());
        restaurantDto.setTitle(restaurant.getName());
        restaurantDto.setId(restaurant.getId());

        if (user.getFavourites().contains(restaurantDto)) {
            user.getFavourites().remove(restaurantDto);
            logger.info("Restaurant removed from user ID: " + user.getId() + "'s favorites.");
        } else {
            user.getFavourites().add(restaurantDto);
            logger.info("Restaurant added to user ID: " + user.getId() + "'s favorites.");
        }

        userRepository.save(user);
        return restaurantDto;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {
        logger.info("Toggling status for restaurant with ID: " + id);
        Restaurant restaurant = findRestaurantById(id);
        restaurant.setOpen(!restaurant.isOpen());

        Restaurant updatedRestaurant = restaurantRepository.save(restaurant);
        logger.info("Restaurant status updated to " + (updatedRestaurant.isOpen() ? "open" : "closed") + " for restaurant ID: " + id);
        return updatedRestaurant;
    }
}

package com.FoodGuy.Service;

import com.FoodGuy.Model.Food;
import com.FoodGuy.Model.FoodCategory;
import com.FoodGuy.Model.Restaurant;
import com.FoodGuy.Request.CreateFoodRequest;

import java.util.List;

public interface FoodService {


    public Food createFood(CreateFoodRequest  req , FoodCategory category , Restaurant restaurant);

    void deletFood(Long foodId) throws Exception;

    public List<Food> getRestaurantFood(Long restuarantId
            ,boolean isVegeterian
            ,boolean isNonVeg
            ,boolean isSeason
            ,String foodCategory );

     public List<Food> SearchFood(String keyword);

     public Food findFoodById(Long Id) throws  Exception;

     public Food updateAvailabilityStatus (Long foodId) throws  Exception;

}

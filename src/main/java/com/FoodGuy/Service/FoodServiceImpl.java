package com.FoodGuy.Service;

import com.FoodGuy.Model.Food;
import com.FoodGuy.Model.FoodCategory;
import com.FoodGuy.Model.Restaurant;
import com.FoodGuy.Repository.FoodRepository;
import com.FoodGuy.Request.CreateFoodRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements  FoodService{



    private final FoodRepository foodRepository ;

    public FoodServiceImpl(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }


    @Override
    public Food createFood(CreateFoodRequest req, FoodCategory category, Restaurant restaurant) {

        Food food  = new Food();
        food.setFoodCategory(category);
        food.setRestaurant((restaurant));
        food.setDescription((req.getDescription()));
        food.setImages(req.getImages());
        food.setName(req.getName());
        food.setPrice(req.getPrice());
        food.setIngredientItems(req.getIngredientItems());
        food.setSeasonal(req.isSeasional());
        food.setVeg(req.isVegetarin());

        Food savedFood = foodRepository.save(food);
        restaurant.getFoods().add(savedFood);
        return  savedFood;
    }

    @Override
    public void deletFood(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setRestaurant(null);
        foodRepository.save(food);


    }

    @Override
    public List<Food> getRestaurantFood(Long restuarantId
            ,boolean isVegeterian
            ,boolean isNonVeg
            ,boolean isSeason
            ,String foodCategory) {

         List<Food> foods = foodRepository.findByRestaurantId(restuarantId);

         if(isVegeterian){
             foods=filerByVegeterian(foods,isVegeterian);
         } else if (isNonVeg) {
             foods=filerByNonVegeterian(foods,isNonVeg);
         } else if (isSeason) {
             foods=filerBySeason(foods,isSeason);
         } else if (foodCategory != null && !foodCategory.equals("")) {
             foods=filerByFoodCategory(foods,foodCategory);
         }
        return  foods;
    }

    private List<Food> filerByFoodCategory(List<Food> foods, String foodCategory) {
        return foods.stream().filter(food-> {
            if(food.getFoodCategory()!=null){
                return food.getFoodCategory().getName().equals(foodCategory);
            }
            return false;
        }).collect(Collectors.toList());
    }

    private List<Food> filerBySeason(List<Food> foods, boolean isSeason) {
       return   foods.stream().filter(fil-> fil.isSeasonal() == isSeason).collect(Collectors.toList());
    }

    private List<Food> filerByNonVegeterian(List<Food> foods, boolean isNonVeg) {
        return foods.stream().filter(fil-> !fil.isVeg()).collect(Collectors.toList());
    }

    private List<Food> filerByVegeterian(List<Food> foods, boolean isVegeterian) {

        return foods.stream().filter(fillterr->fillterr.isVeg()==isVegeterian).collect(Collectors.toList());
    }

    @Override
    public List<Food> SearchFood(String keyword) {
        return foodRepository.searchFood(keyword);
    }

    @Override
    public Food findFoodById(Long Id) throws Exception {

        Optional<Food>  optFood = foodRepository.findById(Id);
        if(optFood.isEmpty()){
            throw new Exception("Desired Food Not Found");
        }
        return optFood.get();
    }

    @Override
    public Food updateAvailabilityStatus(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setAvailable(!food.isAvailable());
        return  foodRepository.save(food);

    }
}

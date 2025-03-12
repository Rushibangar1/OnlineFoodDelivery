package com.FoodGuy.Request;

import com.FoodGuy.Model.FoodCategory;
import com.FoodGuy.Model.IngredientItems;
import java.util.List;


public class CreateFoodRequest{
    private String name;
    private String description;
    private Long   price;
    private FoodCategory  category;
    private List<String> images;
    private Long restuarantId;
    private boolean vegetarin;
    private boolean seasional;
    private List<IngredientItems> ingredientItems ;


    public CreateFoodRequest() {
    }

    public CreateFoodRequest(String name, String description, Long price, FoodCategory category, List<String> images, Long restuarantId, boolean vegetarin, boolean seasional, List<IngredientItems> ingredientItems) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.images = images;
        this.restuarantId = restuarantId;
        this.vegetarin = vegetarin;
        this.seasional = seasional;
        this.ingredientItems = ingredientItems;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public FoodCategory getCategory() {
        return category;
    }

    public void setCategory(FoodCategory category) {
        this.category = category;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Long getRestuarantId() {
        return restuarantId;
    }

    public void setRestuarantId(Long restuarantId) {
        this.restuarantId = restuarantId;
    }

    public boolean isVegetarin() {
        return vegetarin;
    }

    public void setVegetarin(boolean vegetarin) {
        this.vegetarin = vegetarin;
    }

    public boolean isSeasional() {
        return seasional;
    }

    public void setSeasional(boolean seasional) {
        this.seasional = seasional;
    }

    public List<IngredientItems> getIngredientItems() {
        return ingredientItems;
    }

    public void setIngredientItems(List<IngredientItems> ingredientItems) {
        this.ingredientItems = ingredientItems;
    }
}

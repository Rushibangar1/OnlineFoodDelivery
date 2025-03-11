package com.FoodGuy.Request;

import com.FoodGuy.Model.Address;
import com.FoodGuy.Model.ContactInformation;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateRestaurantRequest {

    private  String name;
    private String description;
    private String cuisineType;
    private Address address;
    private ContactInformation contactInformation;
    private String openingHours;
    private List<String> images;

}

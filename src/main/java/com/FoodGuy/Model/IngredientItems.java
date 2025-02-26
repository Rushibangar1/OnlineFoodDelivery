package com.FoodGuy.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientItems {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private long id;


    private String name;

    @ManyToOne
    private IngredientCategory ingredientCategory;


    @ManyToOne
    @JsonIgnore
    private Restaurant restaurant;

    private boolean inStock = true;






}

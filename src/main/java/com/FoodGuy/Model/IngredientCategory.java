package com.FoodGuy.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    private  String name;

    @ManyToOne
    @JsonIgnore
    private Restaurant restaurant;


    @OneToMany(mappedBy = "ingredientCategory",cascade = CascadeType.ALL)
    private List<IngredientItems> ingredientItemsList = new ArrayList<>();







}

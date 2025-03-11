package com.FoodGuy.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private User owner;

    private String name;
    private String description;
    private String cuisineType;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @Embedded
    private ContactInformation contactInformation;

    private String openingHours;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orderList = new ArrayList<>();

    @ElementCollection
    @Column(length = 1000)
    private List<String> images;

    private LocalDateTime registrationDate;
    private boolean open = true; // Default to open

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Food> foods = new ArrayList<>();


    public Restaurant(Address address, ContactInformation contactInformation, String cuisineType, String description,
                      List<String> images, String name, String openingHours, LocalDateTime registrationDate, User owner) {
        this.address = address;
        this.contactInformation = contactInformation;
        this.cuisineType = cuisineType;
        this.description = description;
        this.images = images;
        this.name = name;
        this.openingHours = openingHours;
        this.registrationDate = registrationDate;
        this.owner = owner;
        this.open = true; // Set restaurant as open by default
    }
}

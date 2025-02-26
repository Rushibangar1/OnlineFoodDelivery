package com.FoodGuy.Model;


import com.FoodGuy.Dto.RestaurantDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;



@NoArgsConstructor
@AllArgsConstructor
@Entity

public class User {

      @Id
      @GeneratedValue(strategy = GenerationType.AUTO)
      private long id;

      private String fullName;

      private String email;

      @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
      private String password;

      @Enumerated(EnumType.STRING)
      private USER_ROLE role ;

      @JsonIgnore
      @OneToMany(cascade = CascadeType.ALL,mappedBy = "customer")
      private List<Order> order = new ArrayList<>();

      @ElementCollection
      private List<RestaurantDto> favourites = new ArrayList<>();

      @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
      private List<Address> userAdress = new ArrayList<>();

      public long getId() {
            return id;
      }

      public void setId(long id) {
            this.id = id;
      }

      public String getFullName() {
            return fullName;
      }

      public void setFullName(String fullName) {
            this.fullName = fullName;
      }

      public String getEmail() {
            return email;
      }

      public void setEmail(String email) {
            this.email = email;
      }

      public String getPassword() {
            return password;
      }

      public void setPassword(String password) {
            this.password = password;
      }

      public USER_ROLE getRole() {
            return role;
      }

      public void setRole(USER_ROLE role) {
            this.role = role;
      }

      public List<Order> getOrder() {
            return order;
      }

      public void setOrder(List<Order> order) {
            this.order = order;
      }

      public List<RestaurantDto> getFavourites() {
            return favourites;
      }

      public void setFavourites(List<RestaurantDto> favourites) {
            this.favourites = favourites;
      }

      public List<Address> getUserAdress() {
            return userAdress;
      }

      public void setUserAdress(List<Address> userAdress) {
            this.userAdress = userAdress;
      }
}

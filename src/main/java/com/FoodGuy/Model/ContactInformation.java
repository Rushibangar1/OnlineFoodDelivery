package com.FoodGuy.Model;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ContactInformation {
    private String email;
    private String mobile;
    private String instagram;
    private String x;
}

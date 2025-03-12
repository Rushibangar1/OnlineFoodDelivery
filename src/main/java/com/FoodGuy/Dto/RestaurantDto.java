package com.FoodGuy.Dto;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.util.List;
import java.util.Objects;


@Embeddable
public class RestaurantDto {
    private String title;
    @Column(length = 1000)
    private List<String> images;
    private String description;
    private long   id;

    public RestaurantDto(String title, List<String> images, String description, long id) {
        this.title = title;
        this.images = images;
        this.description = description;
        this.id = id;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true; // Same object reference
//        if (o == null || getClass() != o.getClass()) return false; // Check class type
//        RestaurantDto that = (RestaurantDto) o;
//        return Objects.equals(title, that.title); // Compare by title
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(title);
//    }


    public RestaurantDto() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

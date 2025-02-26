package com.FoodGuy.UserService;

import com.FoodGuy.Model.User;

import java.util.Optional;

public interface UserService {


    public User findUserByJwtToken(String jwt) throws Exception;

    public Optional<User> finUserByEmail(String email) throws Exception;

}

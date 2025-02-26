package com.FoodGuy.Service;

import com.FoodGuy.Dao.UserDataLayer;
import com.FoodGuy.Model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    private UserDataLayer  userDataLayer;

    public UserService(UserDataLayer userDataLayer){

        this.userDataLayer = userDataLayer;
    }



    public User GetuserData() {
        return  userDataLayer.findUser();
    }
}

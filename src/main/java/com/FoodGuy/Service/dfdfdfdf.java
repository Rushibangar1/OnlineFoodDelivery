package com.FoodGuy.Service;

import com.FoodGuy.Dao.UserDataLayer;
import com.FoodGuy.Model.User;
import org.springframework.stereotype.Service;

@Service
public class dfdfdfdf {


    private UserDataLayer  userDataLayer;

    public dfdfdfdf(UserDataLayer userDataLayer){

        this.userDataLayer = userDataLayer;
    }



    public User GetuserData() {
        return  userDataLayer.findUser();
    }
}

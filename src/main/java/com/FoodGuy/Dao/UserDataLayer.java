package com.FoodGuy.Dao;


import com.FoodGuy.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDataLayer {

    @Autowired
     private JdbcTemplate jdbcTemplate;

    //Testing purpose // Data will be passed via the method
     public int id = 1;

    public User findUser() {
        return new User();
    }


//     public User findUser() {
//        String query = "Select * from User where id = ?";
//        return jdbcTemplate.query(sql, new Object[]{"%" + id + "%"} , (rs,rowNum) ->{
//            User user = new User();
//            user.setId(rs.getLong("id"));
//        });
//     }

     /*
      return jdbcTemplate.query(sql, new Object[]{"%" + name + "%", "%" + email + "%"}, (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            return user;
        });
      */
     }

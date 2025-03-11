package com.FoodGuy.Repository;

import com.FoodGuy.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {


    Optional <User> findByEmail(String userName);

}





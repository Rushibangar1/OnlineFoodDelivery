package com.FoodGuy.Repository;

import com.FoodGuy.Model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository  extends JpaRepository<Address ,Long> {

}

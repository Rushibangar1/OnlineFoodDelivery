package com.FoodGuy.controller;

import com.FoodGuy.Model.User;
import com.FoodGuy.Service.dfdfdfdf;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/greet")
public class HelloController {


    private dfdfdfdf userService;

    public HelloController(dfdfdfdf userService) {
        this.userService = userService;
    }

    @GetMapping("hello")
    public String sayHello(){
        return "This is a test Message";
    }



    @GetMapping("testDatabase")
    public ResponseEntity<User> GetUserData(){
        User userData = userService.GetuserData();
        return ResponseEntity.ok(userData);
    }
    @PostMapping("/test")
    public ResponseEntity<String> testing() {
        return ResponseEntity.ok("Hello");
    }
}

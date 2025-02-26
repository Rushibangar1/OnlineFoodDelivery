package com.FoodGuy.controller;

import com.FoodGuy.Config.JwtProvider;
import com.FoodGuy.Dto.SignupDto;
import com.FoodGuy.Model.Cart;
import com.FoodGuy.Model.USER_ROLE;
import com.FoodGuy.Model.User;
import com.FoodGuy.Repository.CartRepository;
import com.FoodGuy.Repository.UserRepository;
import com.FoodGuy.Request.LoginRequest;
import com.FoodGuy.UserService.CustomUserService;
import com.FoodGuy.response.AuthResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")

public class AuthController {

    //Constructor based auto wiring
    private final UserRepository    userRepository;
    private final PasswordEncoder   passwordEncoder;
    private final JwtProvider       jwtProvider;
    private final CustomUserService customUserService;
    private final CartRepository    cartRepository;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider, CustomUserService customUserService, CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.customUserService = customUserService;
        this.cartRepository = cartRepository;
    }



    @PostMapping("/signup")
    public ResponseEntity<AuthResponse>CreteUserHandler(@RequestBody SignupDto _user) throws Exception {

        System.out.println("This is the user Role"+_user.getRole());
        Optional<User> isEmailExist = userRepository.findByEmail(_user.getEmail());
        if(isEmailExist.isPresent()){
            throw new Exception("Email is already used with another account");
        }
        User createdUser = new User();
        createdUser.setEmail(_user.getEmail());
        createdUser.setFullName(_user.getFullName());
        createdUser.setRole(_user.getRole());
        createdUser.setPassword(passwordEncoder.encode(_user.getPassword()));

        User savedUser = userRepository.save(createdUser);
        Cart cart = new Cart();
        cart.setCustomer(savedUser);
        cartRepository.save(cart);

        Authentication authentication = new UsernamePasswordAuthenticationToken(_user.getEmail(),_user.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String  jwtToken = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwtToken);
        authResponse.setMessage("Register Success");
        authResponse.setRole(savedUser.getRole());

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }


    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signUp(@RequestBody LoginRequest request){

        String userName = request.getEmail();
        String password = request.getPassword();
        Authentication authentication = authenticate(userName,password);
        return null;
    }

    private Authentication authenticate(String userName, String password) {


        return null;
    }


}

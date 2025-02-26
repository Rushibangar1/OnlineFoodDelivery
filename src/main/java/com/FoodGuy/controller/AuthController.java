package com.FoodGuy.controller;

import com.FoodGuy.Config.JwtProvider;
import com.FoodGuy.Dto.SignupDto;
import com.FoodGuy.Model.Cart;
import com.FoodGuy.Model.User;
import com.FoodGuy.Repository.CartRepository;
import com.FoodGuy.Repository.UserRepository;
import com.FoodGuy.Request.LoginRequest;
import com.FoodGuy.UserService.CustomUserService;
import com.FoodGuy.response.AuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")  // Base path for authentication-related endpoints
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class); // Logger instance

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserService customUserService;
    private final CartRepository cartRepository;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider,
                          CustomUserService customUserService, CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.customUserService = customUserService;
        this.cartRepository = cartRepository;
    }

    /**
     * Handles user registration.
     */
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody SignupDto _user) {
        logger.info("Received signup request for email: {}", _user.getEmail());

        if (userRepository.findByEmail(_user.getEmail()).isPresent()) {
            logger.warn("Signup failed: Email {} already exists", _user.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is already used with another account");
        }

        User savedUser = createUser(_user);
        logger.info("User registered successfully: {}", savedUser.getEmail());

        Authentication authentication = new UsernamePasswordAuthenticationToken(_user.getEmail(), _user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.status(HttpStatus.CREATED).body(generateAuthResponse(authentication, savedUser));
    }

    /**
     * Handles user login.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest request) {
        logger.info("Login attempt for email: {}", request.getEmail());

        try {
            Authentication authentication = authenticate(request.getEmail(), request.getPassword());
            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

            logger.info("Login successful for user: {}", request.getEmail());
            return ResponseEntity.ok(generateAuthResponse(authentication, user));

        } catch (BadCredentialsException e) {
            logger.error("Login failed for user: {}", request.getEmail());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
    }

    /**
     * Creates a new user and assigns a role.
     */
    private User createUser(SignupDto _user) {
        logger.info("Creating a new user: {}", _user.getEmail());

        User user = new User();
        user.setEmail(_user.getEmail());
        user.setFullName(_user.getFullName());
        user.setRole(_user.getRole());
        user.setPassword(passwordEncoder.encode(_user.getPassword()));

        User savedUser = userRepository.save(user);

        Cart cart = new Cart();
        cart.setCustomer(savedUser);
        cartRepository.save(cart);

        logger.info("User {} registered and cart initialized.", savedUser.getEmail());
        return savedUser;
    }

    /**
     * Authenticates user credentials.
     */
    private Authentication authenticate(String email, String password) {
        logger.info("Authenticating user: {}", email);

        UserDetails userDetails = customUserService.loadUserByUsername(email);
        if (userDetails == null || !passwordEncoder.matches(password, userDetails.getPassword())) {
            logger.warn("Authentication failed for user: {}", email);
            throw new BadCredentialsException("Invalid username or password");
        }

        logger.info("Authentication successful for user: {}", email);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    /**
     * Generates authentication response with JWT.
     */
    private AuthResponse generateAuthResponse(Authentication authentication, User user) {
        String jwtToken = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwtToken);
        authResponse.setMessage("Success");
        authResponse.setRole(user.getRole());

        logger.info("JWT token generated for user: {}", user.getEmail());
        return authResponse;
    }





}

package com.FoodGuy.UserService;

import com.FoodGuy.Config.JwtProvider;
import com.FoodGuy.Logger.BaseLogger;
import com.FoodGuy.Model.User;
import com.FoodGuy.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl extends BaseLogger implements UserService  {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        logger.info("Finding user by JWT token"); //

        String email = jwtProvider.getEmailFromJwtToken(jwt);
        logger.debug("Extracted email from JWT: {}", email); //

        return finUserByEmail(email)
                .orElseThrow(() -> {
                    logger.error("User not found for email: {}", email);
                    return new Exception("User Not found");
                });
    }

    @Override
    public Optional<User> finUserByEmail(String email) {
        logger.info("Searching user by email: {}", email);

        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            logger.debug("User found: {}", user.get().getEmail());
        } else {
            logger.warn("No user found for email: {}", email);
        }

        return user;
    }
}

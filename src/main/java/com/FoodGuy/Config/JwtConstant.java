package com.FoodGuy.Config;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtConstant {

    public static final String SECRET_KEY = "your-very-secure-32-characters-long-key";
   // public static final String SECRET_KEY   = String.valueOf(Keys.secretKeyFor(SignatureAlgorithm.HS256));
    public static final String JWT_HEADER   = "Authorization";



}

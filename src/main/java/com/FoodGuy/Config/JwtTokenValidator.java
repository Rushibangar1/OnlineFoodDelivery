package com.FoodGuy.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JwtTokenValidator extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenValidator.class);
    private final SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        logger.info("Incoming request: {}", request.getRequestURI());

        String jwt = request.getHeader(JwtConstant.JWT_HEADER);

        if (jwt == null || !jwt.startsWith("Bearer ")) {
            logger.warn("No valid JWT found in request header");
        } else {
            jwt = jwt.substring(7); // Remove "Bearer " prefix
            try {
                logger.info("Validating JWT token...");

                Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

                String email = String.valueOf(claims.get("email"));
                Object authoritiesObject = claims.get("authorities");

                List<GrantedAuthority> auth;

                if (authoritiesObject instanceof String) {
                    auth = AuthorityUtils.commaSeparatedStringToAuthorityList((String) authoritiesObject);
                } else if (authoritiesObject instanceof List) {
                    List<?> authoritiesList = (List<?>) authoritiesObject;
                    String roles = authoritiesList.stream().map(String::valueOf).collect(Collectors.joining(","));
                    auth = AuthorityUtils.commaSeparatedStringToAuthorityList(roles);
                } else {
                    auth = AuthorityUtils.NO_AUTHORITIES;
                }

                logger.info("Extracted Email: {}", email);
                auth.forEach(grantedAuthority -> logger.info("Granted Authority: {}", grantedAuthority.getAuthority()));

                Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, auth);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.info("User {} authenticated successfully", email);

            } catch (Exception e) {
                logger.error("Invalid JWT token: {}", e.getMessage());
                throw new BadCredentialsException("Invalid Token - " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}

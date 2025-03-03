package com.poly.app.infrastructure.security;


import com.poly.app.domain.model.Customer;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtilities jwtUtilities;
    private final CustomUserDetailsService customerUserDetailsService;


    @Autowired
    private HttpSession session;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("Cos chajy qua ma");

        String token = jwtUtilities.getToken(request);
        System.out.println(token);
        if (token != null && jwtUtilities.validateToken(token)) {
            String id = jwtUtilities.extractUserId(token);
            String username = jwtUtilities.extractUsername(token);
            String userType = jwtUtilities.extractRoleName(token);
            System.out.println(id + userType + username);
            UserDetails userDetails = null;
            if ("ROLE_STAFF".equals(userType)) {
                userDetails = customerUserDetailsService.loadUserByUsername(username);
            } else if ("ROLE_USER".equals(userType)) {
                userDetails = customerUserDetailsService.loadUserByUsername(username); // CustomerService logic
            }
            System.out.println("userDetails" +userDetails);
            System.out.println( "jwtUtilities.validateToken(token)" + jwtUtilities.validateToken(token));
            if (userDetails != null && jwtUtilities.validateToken(token)) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails.getUsername(), null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                session.setAttribute("user", userDetails);
            }
        }
        filterChain.doFilter(request, response);
    }

}

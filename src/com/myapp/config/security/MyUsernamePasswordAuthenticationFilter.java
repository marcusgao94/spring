package com.myapp.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.model.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.util.ArrayList;

/**
 * Created by gy on 16/7/18.
 */
public class MyUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request, HttpServletResponse response) {
        //json transformation
        ObjectMapper mapper = new ObjectMapper();
        User user;
        try {
             user = mapper.readValue(request.getInputStream(), User.class);
        } catch (Exception e) {
            user = new User();
            e.printStackTrace();
        }
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        setDetails(request, token);
        return getAuthenticationManager().authenticate(token);
    }
}

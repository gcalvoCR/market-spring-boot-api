package com.gabriel.marketplace.web.security.filter;

import com.gabriel.marketplace.domain.service.MarketUserDetailsService;
import com.gabriel.marketplace.web.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilterRequest extends OncePerRequestFilter {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private MarketUserDetailsService marketUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        //If the condition is true we grab the web token
        if(authorizationHeader !=null && authorizationHeader.startsWith("Bearer")){
            //We strip out the Bearer part
            String jwt = authorizationHeader.substring(7);
            String username = jwtUtil.extractUsername(jwt);

            // We verify that the user is not null
            //That it has not entered our API
            if(username != null && SecurityContextHolder.getContext().getAuthentication() ==null){
                // we verify that the user exists
                UserDetails userDetails = marketUserDetailsService.loadUserByUsername(username);

                if(jwtUtil.validateToken(jwt, userDetails)){
                    // we create a new authToken, sending user Details, no particular password, and roles.
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    // We set the details that it is receiving
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // We set the context so it doesn't have to validate again next time
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}

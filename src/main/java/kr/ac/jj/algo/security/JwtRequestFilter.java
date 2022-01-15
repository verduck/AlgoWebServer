package kr.ac.jj.algo.security;

import kr.ac.jj.algo.domain.User;
import kr.ac.jj.algo.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserService userService;

    @Autowired
    public JwtRequestFilter(final JwtService jwtService, final UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    
        final String authorizationHeader = request.getHeader("Authorization");
        Integer userId = -1;
        String token = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
          token = authorizationHeader.substring(7);
          try {
            userId = jwtService.getUserId(token);
          } catch (IllegalArgumentException e) {
                    System.out.println("Unable to get JWT Token");
                } catch (ExpiredJwtException e) {
                    System.out.println("JWT Token has expired");
                }
        }

        if (userId != -1 && SecurityContextHolder.getContext().getAuthentication() == null) {
          User user = userService.loadUserById(userId);

          if (jwtService.validateToken(token, user)) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            user, null, user.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
          }
        }
        filterChain.doFilter(request, response);
    }
  
}

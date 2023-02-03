package aprendendo.api.books.config;

import aprendendo.api.books.auth.TokenService;
import aprendendo.api.books.model.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter extends OncePerRequestFilter {
    private final TokenService tokenService;

    public AuthFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
    throws ServletException, IOException {
        String token = getTokenFromHeader(req);
        if(tokenService.isValid(token)) {
            authenticate(token);
        }
        doFilter(req,res,filterChain);
    }

    public String getTokenFromHeader(HttpServletRequest req) {
        String token = req.getHeader("Authorization");
        if(token == null || !token.startsWith("Bearer ")) {
            return null;
        }

        return token.split(" ")[1];
    }

    public void authenticate(String token) {
        User user = tokenService.getUserFromToken(token);
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(user.toDTO(),null,user.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(usernamePassword);
    }
}

package dislinkt.messagingservice.security.auth;

import dislinkt.messagingservice.dto.Authority;
import dislinkt.messagingservice.exception.InvalidToken;
import io.jsonwebtoken.Claims;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;
import dislinkt.messagingservice.security.TokenUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class TokenAuthenticationFilter extends OncePerRequestFilter {

	private TokenUtils tokenUtils;
    private String authServiceAddress;

    public TokenAuthenticationFilter(TokenUtils tokenHelper, String authServiceAddress) {
        this.tokenUtils = tokenHelper;
        this.authServiceAddress = authServiceAddress;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String authToken = tokenUtils.getToken(request);

        if (authToken != null) {
            // uzmi username iz tokena
            String username = tokenUtils.getUsernameFromToken(authToken);
            Claims claims = tokenUtils.getAllClaimsFromToken(authToken);
            if (username != null) {
                RestTemplate restTemplate = new RestTemplate();
                String fooResourceUrl = authServiceAddress + "/authentication/users/check-username/" + username;
                ResponseEntity<Boolean> restTemplateResponse = restTemplate.getForEntity(fooResourceUrl, Boolean.class);
                if (!restTemplateResponse.getBody()) {
                    throw new InvalidToken("Username not found on authentication service.");
                }

                List<String> authoritiesNames = (List<String>) claims.get("authorities");
                Long id = Long.parseLong(claims.get("id") + "");

                List<Authority> authorities = new ArrayList<>();
                authoritiesNames.forEach(name -> authorities.add(new Authority(1L, name)));

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(id, null, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }

}

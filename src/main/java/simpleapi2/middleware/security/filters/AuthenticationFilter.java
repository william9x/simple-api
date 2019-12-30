package simpleapi2.middleware.security.filters;

import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.security.auth.message.AuthException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationFilter implements Filter {

    private final String API_KEY = "simple_api_key_for_authentication";

    @SneakyThrows
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String authenticationHeader = req.getHeader("Authentication");

        if (false == authenticationHeader.equals(API_KEY)) {
            res.setStatus(HttpStatus.UNAUTHORIZED.value());
            throw new RuntimeException("Access denied");
        }


        chain.doFilter(request, response);
    }
}

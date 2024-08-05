package com.challenge.operations.auth.filters;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.challenge.operations.Entities.User;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.challenge.operations.auth.TokenJwtConfing.*;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

    
    private AuthenticationManager authenticationManager;

    
   // The `public JWTAuthenticationFilter(AuthenticationManager authenticationManager)` constructor in
   // the `JWTAuthenticationFilter` class is initializing the `authenticationManager` property of the
   // class with the provided `AuthenticationManager` instance. This constructor allows the
   // `JWTAuthenticationFilter` to receive an `AuthenticationManager` object as a parameter when it is
   // instantiated, and then sets the internal `authenticationManager` property to this provided object
   // for later use in the class methods. This is a common practice in Java to inject dependencies into
   // classes through constructors for better decoupling and flexibility in managing dependencies.
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * The `attemptAuthentication` function reads user credentials from the request input stream,
     * creates an authentication token, and authenticates it using the authentication manager.
     * 
     * @param request The `request` parameter in the `attemptAuthentication` method is of type
     * `HttpServletRequest`. It represents the HTTP request made by the client to the server. This
     * object contains information about the request such as headers, parameters, and the request body.
     * @param response The `response` parameter in the `attemptAuthentication` method of a Spring
     * Security `AuthenticationProvider` is used to send a response back to the client in case of
     * authentication failure. This response can include error messages or status codes to indicate the
     * outcome of the authentication attempt. It is typically used to communicate
     * @return The `attemptAuthentication` method is returning the result of calling
     * `authenticationManager.authenticate(authToken)`, which is the authentication manager's attempt
     * to authenticate the user credentials provided in the `UsernamePasswordAuthenticationToken`
     * object `authToken`.
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        User user = null;
        String username = null;
        String password = null;
        
        try {
            user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            username = user.getUsername();
            password = user.getPassword();

            //logger.info("Username desde request InputStream (raw) " + username);
            //logger.info("Password desde request InputStream (raw) " + password);

        } catch (StreamReadException e) {
            e.printStackTrace();
        } catch (DatabindException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authToken);
    }

   /**
    * The `successfulAuthentication` function generates a token, adds it to the response header, and
    * returns a JSON response with a success message and username after a successful authentication.
    * 
    * @param request The `request` parameter in the `successfulAuthentication` method represents the
    * HTTP request made by the client to the server. It contains information such as request headers,
    * parameters, and body content sent by the client. This parameter is used to retrieve information
    * from the incoming request or to modify the response that will
    * @param response The `response` parameter in the `successfulAuthentication` method is an object of
    * the `HttpServletResponse` class. It is used to manipulate the HTTP response that will be sent
    * back to the client after a successful authentication process. In the provided code snippet,
    * various operations are performed on the `response`
    * @param chain The `chain` parameter in the `successfulAuthentication` method is an object of type
    * `FilterChain`. The `FilterChain` interface is used to invoke the next filter in the chain or the
    * resource at the end of the chain. It provides a way to pass along the request and response to the
    * @param authResult The `authResult` parameter in the `successfulAuthentication` method represents
    * the result of a successful authentication process. It contains information about the
    * authenticated user, such as their username, authorities, and any additional details associated
    * with the authentication.
    */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

        String username = ((org.springframework.security.core.userdetails.User) authResult.getPrincipal())
                .getUsername();
        
        String token = Jwts.builder()
                                .setSubject(username)
                                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                                .setIssuedAt(new Date())
                                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                                .compact();

        response.addHeader(HEADER_AUTHORIZATION, PREFIX_TOKEN + token);

        Map<String, Object> body = new HashMap<>();
        body.put("token", token);
        body.put("message", String.format("Hola %s, has iniciado sesion con exito!", username));
        body.put("username", username);
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(200);
        response.setContentType("application/json");
    }

    /**
     * The `unsuccessfulAuthentication` method in Java handles failed authentication by returning a
     * JSON response with an error message and status code 401.
     * 
     * @param request The `request` parameter in the `unsuccessfulAuthentication` method represents the
     * HTTP request made by the client during the authentication process. It contains information such
     * as the request headers, parameters, and other details sent by the client to the server.
     * @param response The `response` parameter in the `unsuccessfulAuthentication` method is an object
     * of type `HttpServletResponse`. It is used to send the response back to the client who made the
     * authentication request. In this method, the response is being customized to return a JSON
     * response with an error message and status code
     * @param failed The `failed` parameter in the `unsuccessfulAuthentication` method represents the
     * `AuthenticationException` that occurred during the authentication process. This exception
     * contains information about the specific reason for the authentication failure, such as invalid
     * credentials, account locked, expired credentials, etc.
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {

        Map<String, Object> body = new HashMap<>();
        body.put("message", "Error en la autenticacion username o password incorrecto!");
        body.put("error", failed.getMessage());

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(401);
        response.setContentType("application/json");
    }
    
}

package matheus.github.calculate.security.filters;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.MissingClaimException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import matheus.github.calculate.exception.CustomizedExceptionResponse;
import matheus.github.calculate.exception.controlleradvice.HeaderControllerAdvice;
import matheus.github.calculate.exception.controlleradvice.JwtControllerAdvice;
import matheus.github.calculate.exception.exceptions.InvalidAuthenticationHeaderException;
import matheus.github.calculate.jwt.JwtService;
import matheus.github.calculate.security.UnprotectedEndpoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class ValidateJwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

	@Autowired
	private JwtControllerAdvice jwtControllerAdvice;

	@Autowired
	private HeaderControllerAdvice headerControllerAdvice;

	@Autowired
	private CustomizedExceptionResponse customizedExceptionResponse;

	@Autowired
	private UnprotectedEndpoints unprotectedEndpoints;

	private static final String BEARER_PREFIX = "Bearer ";

	@Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String token = null;
		try {
			token = extractTokenFromRequest(request);
		} catch (InvalidAuthenticationHeaderException exception) {
			var responseEntity = headerControllerAdvice.handleInvalidAuthenticationHeaderException(exception);
			customizedExceptionResponse.modifyResponse(response, responseEntity);
			return;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		DecodedJWT decodedJWT = null;
		try {
			decodedJWT = jwtService.getDecodedToken(token);
		} catch (AlgorithmMismatchException exception) {
			var responseEntity = jwtControllerAdvice.handleAlgorithmMismatchException(exception);
			customizedExceptionResponse.modifyResponse(response, responseEntity);
			return;
		} catch (TokenExpiredException exception) {
			var responseEntity = jwtControllerAdvice.handleTokenExpiredException(exception);
			customizedExceptionResponse.modifyResponse(response, responseEntity);
			return;
		} catch (JWTVerificationException exception) {
			var responseEntity = jwtControllerAdvice.handleJWTVerificationException(exception);
			customizedExceptionResponse.modifyResponse(response, responseEntity);
			return;
		}

		var authorities = getAuthoritiesFromDecodedToken(decodedJWT);
		var username = getUsernameFromDecodedToken(decodedJWT);

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
				username,
				null,
				authorities);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		filterChain.doFilter(request, response);
	}

	private Collection<? extends GrantedAuthority> getAuthoritiesFromDecodedToken(DecodedJWT decodedJWT) {
		String authority = "ROLE_" + decodedJWT.getClaim(JwtService.JWT_AUTHORITY_CLAIM).asString();
		return Collections.singletonList(new SimpleGrantedAuthority(authority));
	}

	private String getUsernameFromDecodedToken(DecodedJWT decodedJWT) {
		return decodedJWT.getClaim(JwtService.JWT_USERNAME_CLAIM).asString();
	}

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
		String requestPath = request.getRequestURI();
		List<String> endpoints = unprotectedEndpoints.getUnprotectedEndpoints();

		for (String endpoint : endpoints) {
			if (requestPath.startsWith(endpoint)) {
				return true;
			}
		}
		return false;
	}

    private String extractTokenFromRequest(HttpServletRequest request) throws InvalidAuthenticationHeaderException, IOException {
		String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (authorization == null || !authorization.startsWith(BEARER_PREFIX)) {
			throw new InvalidAuthenticationHeaderException("Missing Authorization header");
		}

		return authorization.replace(BEARER_PREFIX, "");
	}

}

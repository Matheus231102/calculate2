package matheus.github.calculate.security.filters;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.MissingClaimException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import matheus.github.calculate.exception.CustomizedExceptionResponse;
import matheus.github.calculate.exception.controlleradvice.HeaderControllerAdvice;
import matheus.github.calculate.exception.controlleradvice.JwtControllerAdvice;
import matheus.github.calculate.exception.exceptions.InvalidAuthenticationHeaderException;
import matheus.github.calculate.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static matheus.github.calculate.controller.paths.PathConstants.*;

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

	@Qualifier("objectMapper")
	@Autowired
	private ObjectMapper objectMapper;

	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String BEARER_PREFIX = "Bearer ";

	@SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String token = null;
		try {
			token = extractTokenFromRequest(request, response);
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
		} catch (MissingClaimException exception) {
			var responseEntity = jwtControllerAdvice.handleMissingClaimException(exception);
			customizedExceptionResponse.modifyResponse(response, responseEntity);
			return;
		} catch (JWTVerificationException exception) {
			var responseEntity = jwtControllerAdvice.handleJWTVerificationException(exception);
			customizedExceptionResponse.modifyResponse(response, responseEntity);
			return;
		}

		var authorities = getAuthoritiesFromDecodedToken(decodedJWT);
		var username = getUsernameFromDecodedToken(decodedJWT);

		var authentication = new UsernamePasswordAuthenticationToken(
			  username,
			  null,
			  authorities);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		filterChain.doFilter(request, response);
	}

	private Collection<? extends GrantedAuthority> getAuthoritiesFromDecodedToken(DecodedJWT decodedJWT) {
		String authoritiesClaim = decodedJWT.getClaim(JwtService.JWT_AUTHORITIES_CLAIM).asString();
		return AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesClaim);
	}

	private String getUsernameFromDecodedToken(DecodedJWT decodedJWT) {
		return decodedJWT.getClaim(JwtService.JWT_USERNAME_CLAIM).asString();
	}

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
	   List<String> paths = List.of(DEFAULT_USER_PATH + LOGIN_PATH, DEFAULT_USER_PATH + REGISTER_PATH);
	   return paths.contains(request.getRequestURI());
    }

    private String extractTokenFromRequest(HttpServletRequest request, HttpServletResponse response) throws InvalidAuthenticationHeaderException, IOException {
		String authorization = request.getHeader(AUTHORIZATION_HEADER);

		if (authorization == null || !authorization.startsWith(BEARER_PREFIX)) {
			throw new InvalidAuthenticationHeaderException("Missing Authorization header");
		}

		return authorization.replace(BEARER_PREFIX, "");
	}

}

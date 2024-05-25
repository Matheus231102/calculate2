package matheus.github.calculate.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.DecodedJWT;
import matheus.github.calculate.exception.exceptions.UserNotFoundException;
import matheus.github.calculate.jwt.algorithm.AlgorithmProvider;
import matheus.github.calculate.models.User;
import matheus.github.calculate.repositories.UserRepository;
import matheus.github.calculate.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class JwtService {

    @Value("${jwt.issuer}")
    private String JWT_ISSUER;

	@Autowired
	private AlgorithmProvider algorithmProvider;

    public static final String JWT_USERNAME_CLAIM = "username";
    public static final String JWT_AUTHORITIES_CLAIM = "authorities";
    private final int HOURS_TO_INCREASE = 8;


    @Autowired
    private UserRepository userRepository;

	@Autowired
	private UserService userService;

    public String getToken(String username) throws UserNotFoundException {
		User user = userService.findByUsername(username);
		return generateToken(user);
    }

    public String generateToken(User user) {
	   try {
		  return JWT.create()
				.withIssuer(JWT_ISSUER)
				.withClaim(JWT_USERNAME_CLAIM, user.getUsername())
				.withClaim(JWT_AUTHORITIES_CLAIM, authoritiesToStringSeparetedComma(user.getAuthorities()) )
				.withExpiresAt(generateExpirationDate(HOURS_TO_INCREASE))
				.sign(algorithmProvider.getAlgorithm());

	   } catch (IllegalArgumentException e) {
		  throw new RuntimeException(e);
	   } catch (JWTCreationException e) {
		  throw new RuntimeException("Error generating token: " + e.getMessage());
	   }
    }

    private String authoritiesToStringSeparetedComma(Collection<? extends GrantedAuthority> authorities) {
	   return authorities.stream()
			 .map(grantedAuthority -> grantedAuthority.toString())
			 .collect(Collectors.joining(", "));
    }

    private Instant generateExpirationDate(int timeInHours) {
	   return increaseExpirationHour(timeInHours).toInstant(ZoneOffset.of("-03:00"));
    }

    private LocalDateTime increaseExpirationHour(int timeInHours) {
	   return LocalDateTime.now().plusHours(timeInHours);
    }

    public DecodedJWT getDecodedToken(String token) {
		try {
            return JWT.require(algorithmProvider.getAlgorithm())
                    .withIssuer(JWT_ISSUER)
                    .build()
                    .verify(token);
        } catch (AlgorithmMismatchException e) {
		  throw new AlgorithmMismatchException("An error occurred while getting the algorithm: " + e.getMessage());
		} catch (TokenExpiredException e) {
            throw new TokenExpiredException("The token was expired at: ", e.getExpiredOn());
        } catch (MissingClaimException e) {
		  throw new RuntimeException("The claim has been forgotten: " + e.getMessage());
		} catch (JWTVerificationException e) {
            throw new RuntimeException("An error occurred while decoding token: " + e.getMessage());
        }
    }

}

package matheus.github.calculate.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import matheus.github.calculate.exception.exceptions.user.UserNotFoundException;
import matheus.github.calculate.jwt.algorithm.AlgorithmProvider;
import matheus.github.calculate.models.User;
import matheus.github.calculate.repositories.UserRepository;
import matheus.github.calculate.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class JwtService {

    @Value("${jwt.issuer}")
    private String JWT_ISSUER;

	@Autowired
	private AlgorithmProvider algorithmProvider;

    public static final String JWT_USERNAME_CLAIM = "username";
    public static final String JWT_AUTHORITY_CLAIM = "authorities";
    private final int HOURS_TO_INCREASE_THE_EXPIRATION_TIME = 8;

	@Autowired
	private UserRepository userRepository;

	@Lazy
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
				.withClaim(JWT_AUTHORITY_CLAIM, user.getRole().name())
				.withExpiresAt(generateExpirationDate(HOURS_TO_INCREASE_THE_EXPIRATION_TIME))
				.sign(algorithmProvider.getAlgorithm());

	   } catch (IllegalArgumentException e) {
		  throw new RuntimeException(e);
	   } catch (JWTCreationException e) {
		  throw new RuntimeException("Error generating token: " + e.getMessage());
	   }
    }

	private Instant generateExpirationDate(int timeInHours) {
	   return increaseExpirationHour(timeInHours).toInstant(ZoneOffset.of("-03:00"));
    }

    private LocalDateTime increaseExpirationHour(int timeInHours) {
	   return LocalDateTime.now().plusHours(timeInHours);
    }

    public DecodedJWT getDecodedToken(String token) {
		return JWT.require(algorithmProvider.getAlgorithm())
				.withIssuer(JWT_ISSUER)
				.build()
				.verify(token);
    }

}

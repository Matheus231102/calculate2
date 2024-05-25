package matheus.github.calculate.jwt.algorithm;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DefaultAlgorithmProvider implements AlgorithmProvider{

	@Value("${jwt.secret}")
	private String SECRET_AUTHENTICATION;

	@Override
	public Algorithm getAlgorithm() {
		try {
			return Algorithm.HMAC256(SECRET_AUTHENTICATION);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Can not define the algorithm");
		}
	}
}

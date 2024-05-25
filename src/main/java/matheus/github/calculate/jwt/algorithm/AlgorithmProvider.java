package matheus.github.calculate.jwt.algorithm;

import com.auth0.jwt.algorithms.Algorithm;

public interface AlgorithmProvider {
	Algorithm getAlgorithm();
}

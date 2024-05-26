package matheus.github.calculate.exception.controlleradvice;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.MissingClaimException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import matheus.github.calculate.exception.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class JwtControllerAdvice {

	@ExceptionHandler(AlgorithmMismatchException.class)
	public ResponseEntity<ExceptionResponse> handleAlgorithmMismatchException(AlgorithmMismatchException exception) {
		String errorMessage = "Error to get the algorithm";
		ExceptionResponse exceptionResponseEntity = ExceptionResponse.builder()
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.error(errorMessage)
				.message(exception.getMessage())
				.build();

		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(exceptionResponseEntity);
	}

	@ExceptionHandler(TokenExpiredException.class)
	public ResponseEntity<ExceptionResponse> handleTokenExpiredException(TokenExpiredException exception) {
		String errorMessage = "Invalid JWT token";
		ExceptionResponse exceptionResponseEntity = ExceptionResponse.builder()
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value())
				.error(errorMessage)
				.message(exception.getMessage())
				.build();

		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(exceptionResponseEntity);
	}

	@ExceptionHandler(MissingClaimException.class)
	public ResponseEntity<ExceptionResponse> handleMissingClaimException(MissingClaimException exception) {
		String errorMessage = "Missing jwt claim";
		ExceptionResponse exceptionResponseEntity = ExceptionResponse.builder()
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.error(errorMessage)
				.message(exception.getMessage())
				.build();

		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(exceptionResponseEntity);
	}


	@ExceptionHandler(JWTVerificationException.class)
	public ResponseEntity<ExceptionResponse> handleJWTVerificationException(JWTVerificationException exception) {
		String errorMessage = "Invalid JWT token";
		ExceptionResponse exceptionResponseEntity = ExceptionResponse.builder()
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value())
				.error(errorMessage)
				.message(exception.getMessage())
				.build();

		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(exceptionResponseEntity);
	}

}

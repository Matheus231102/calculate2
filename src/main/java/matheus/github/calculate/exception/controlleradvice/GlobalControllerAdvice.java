package matheus.github.calculate.exception.controlleradvice;

import matheus.github.calculate.exception.ExceptionResponse;
import matheus.github.calculate.exception.exceptions.InvalidPasswordException;
import matheus.github.calculate.exception.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalControllerAdvice {

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleUserNotFoundException(UserNotFoundException exception) {
		String errorMessage = "User not found";
		ExceptionResponse exceptionResponseEntity = ExceptionResponse.builder()
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.NOT_FOUND.value())
				.error(errorMessage)
				.message(exception.getMessage())
				.build();

		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(exceptionResponseEntity);
	}

	@ExceptionHandler(InvalidPasswordException.class)
	public ResponseEntity<ExceptionResponse> handleInvalidPasswordException(InvalidPasswordException exception) {
		String errorMessage = "Invalid password";
		ExceptionResponse exceptionResponseEntity = ExceptionResponse.builder()
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.UNAUTHORIZED.value())
				.error(errorMessage)
				.message(exception.getMessage())
				.build();

		return ResponseEntity
				.status(HttpStatus.UNAUTHORIZED)
				.body(exceptionResponseEntity);
	}



}

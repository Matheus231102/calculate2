package matheus.github.calculate.exception.controlleradvice;

import matheus.github.calculate.exception.ExceptionResponse;
import matheus.github.calculate.exception.exceptions.user.EmailAlreadyExistsException;
import matheus.github.calculate.exception.exceptions.user.InvalidPasswordException;
import matheus.github.calculate.exception.exceptions.user.InvalidUserException;
import matheus.github.calculate.exception.exceptions.user.UserNotFoundException;
import matheus.github.calculate.exception.exceptions.user.UsernameAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class UserControllerAdvice {

	@ExceptionHandler(InvalidUserException.class)
	public ResponseEntity<ExceptionResponse> handleInvalidUserException(InvalidUserException exception) {
		String errorMessage = "Invalid user";
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

	@ExceptionHandler(UsernameAlreadyExistsException.class)
	public ResponseEntity<ExceptionResponse> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException exception) {
		String errorMessage = "User already exists";
		ExceptionResponse exceptionResponseEntity = ExceptionResponse.builder()
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.CONFLICT.value())
				.error(errorMessage)
				.message(exception.getMessage())
				.build();

		return ResponseEntity
				.status(HttpStatus.CONFLICT)
				.body(exceptionResponseEntity);
	}

	@ExceptionHandler(EmailAlreadyExistsException.class)
	public ResponseEntity<ExceptionResponse> handleEmailAlreadyExistsException(EmailAlreadyExistsException exception) {
		String errorMessage = "User already exists";
		ExceptionResponse exceptionResponseEntity = ExceptionResponse.builder()
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.CONFLICT.value())
				.error(errorMessage)
				.message(exception.getMessage())
				.build();

		return ResponseEntity
				.status(HttpStatus.CONFLICT)
				.body(exceptionResponseEntity);
	}

}

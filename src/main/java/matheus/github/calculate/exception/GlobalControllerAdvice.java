package matheus.github.calculate.exception;

import matheus.github.calculate.exception.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalControllerAdvice {

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ExceptionResponseEntity> userNotFoundException(UserNotFoundException exception) {
		String errorMessage = "User Not Found";
		ExceptionResponseEntity exceptionResponseEntity = ExceptionResponseEntity.builder()
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.NOT_FOUND.value())
				.error(errorMessage)
				.message(exception.getMessage())
				.build();

		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(exceptionResponseEntity);
	}
	//todo criar modified response exception entity
}

package matheus.github.calculate.exception.controlleradvice;

import matheus.github.calculate.exception.ExceptionResponse;
import matheus.github.calculate.exception.exceptions.InvalidFoodException;
import matheus.github.calculate.exception.exceptions.user.FoodNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class FoodControllerAdvice {

	@ExceptionHandler(FoodNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleFoodNotFoundException(FoodNotFoundException exception) {
		String errorMessage = "Food not found";
		ExceptionResponse exceptionResponse = ExceptionResponse.builder()
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.NOT_FOUND.value())
				.error(errorMessage)
				.message(exception.getMessage())
				.build();

		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(exceptionResponse);
	}

	@ExceptionHandler(InvalidFoodException.class)
	public ResponseEntity<ExceptionResponse> handleInvalidFoodException(InvalidFoodException exception) {
		String errorMessage = "Invalid food";
		ExceptionResponse exceptionResponse = ExceptionResponse.builder()
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value())
				.error(errorMessage)
				.message(exception.getMessage())
				.build();

		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(exceptionResponse);
	}

}

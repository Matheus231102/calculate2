package matheus.github.calculate.exception.controlleradvice;

import matheus.github.calculate.exception.ExceptionResponse;
import matheus.github.calculate.exception.exceptions.MealFoodNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class MealFoodControllerAdvice {

	@ExceptionHandler(MealFoodNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleMealFoodNotFoundException(MealFoodNotFoundException exception) {
		String errorMessage = "MealFood not found";
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
}

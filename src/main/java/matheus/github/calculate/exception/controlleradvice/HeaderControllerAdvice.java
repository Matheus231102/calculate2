package matheus.github.calculate.exception.controlleradvice;

import matheus.github.calculate.exception.ExceptionResponse;
import matheus.github.calculate.exception.exceptions.InvalidAuthenticationHeaderException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class HeaderControllerAdvice {

	@ExceptionHandler(InvalidAuthenticationHeaderException.class)
	public ResponseEntity<ExceptionResponse> handleInvalidAuthenticationHeaderException(InvalidAuthenticationHeaderException exception) {
		String errorMessage = "Invalid header";
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

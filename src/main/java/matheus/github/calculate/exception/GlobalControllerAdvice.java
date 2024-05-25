package matheus.github.calculate.exception;

import matheus.github.calculate.exception.exceptions.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerAdvice {

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity userNotFoundException(UserNotFoundException exception) {
		return ResponseEntity.notFound().build();
	}
	//todo criar modified response exception entity
}

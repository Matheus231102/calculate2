package matheus.github.calculate.exception.exceptions;

public class InvalidFoodException extends RuntimeException {
	public InvalidFoodException() {
		super();
	}

	public InvalidFoodException(String message) {
		super(message);
	}
}

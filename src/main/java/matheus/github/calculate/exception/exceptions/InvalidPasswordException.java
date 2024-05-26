package matheus.github.calculate.exception.exceptions;

public class InvalidPasswordException extends RuntimeException {
	public InvalidPasswordException(String message) {
		super(message);
	}

	public InvalidPasswordException() {
		super();
	}
}

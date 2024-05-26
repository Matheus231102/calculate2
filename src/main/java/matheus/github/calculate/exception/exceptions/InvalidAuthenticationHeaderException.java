package matheus.github.calculate.exception.exceptions;

public class InvalidAuthenticationHeaderException extends RuntimeException {
	public InvalidAuthenticationHeaderException() {
		super();
	}

	public InvalidAuthenticationHeaderException(String message) {
		super(message);
	}
}

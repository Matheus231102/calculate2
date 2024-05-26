package matheus.github.calculate.exception.exceptions;

public class InvalidJwtTokenException extends RuntimeException {
	public InvalidJwtTokenException() {
		super();
	}

	public InvalidJwtTokenException(String message) {
		super(message);
	}

	public InvalidJwtTokenException(Throwable cause) {
		super(cause);
	}
}

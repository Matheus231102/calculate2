package matheus.github.calculate.exception.exceptions;

public class EmailAlreadyExistsException extends RuntimeException{
	public EmailAlreadyExistsException() {
		super();
	}

	public EmailAlreadyExistsException(String message) {
		super(message);
	}
}

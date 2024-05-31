package matheus.github.calculate.exception.exceptions.user;

public class EmailAlreadyExistsException extends RuntimeException{
	public EmailAlreadyExistsException() {
		super();
	}

	public EmailAlreadyExistsException(String message) {
		super(message);
	}
}

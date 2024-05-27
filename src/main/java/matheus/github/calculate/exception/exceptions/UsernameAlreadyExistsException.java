package matheus.github.calculate.exception.exceptions;

public class UsernameAlreadyExistsException extends RuntimeException{
	public UsernameAlreadyExistsException() {
		super();
	}

	public UsernameAlreadyExistsException(String message) {
		super(message);
	}
}

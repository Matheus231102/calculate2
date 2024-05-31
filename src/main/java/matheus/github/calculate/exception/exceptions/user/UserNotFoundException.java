package matheus.github.calculate.exception.exceptions.user;

public class UserNotFoundException extends Exception {
	public UserNotFoundException(String message) {
		super(message);
	}

	public UserNotFoundException() {
		super();
	}
}

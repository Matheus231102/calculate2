package matheus.github.calculate.exception.exceptions;

public class MealNotFoundException extends RuntimeException {
	public MealNotFoundException() {
		super();
	}

	public MealNotFoundException(String message) {
		super(message);
	}
}

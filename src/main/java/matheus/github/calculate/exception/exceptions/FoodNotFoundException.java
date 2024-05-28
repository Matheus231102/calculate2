package matheus.github.calculate.exception.exceptions;

public class FoodNotFoundException extends RuntimeException {
	public FoodNotFoundException() {
		super();
	}

	public FoodNotFoundException(String message) {
		super(message);
	}
}

package matheus.github.calculate.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MealFoodRequest {

	@NotNull
	@Size(min = 0)
	private long mealId;

	@NotNull
	@Size(min = 0)
	private long foodId;

	@NotNull
	@Size(min = 0)
	private int foodAmount;
}

package matheus.github.calculate.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RelationshipMealFoodAmountDTO {

	@NotNull
	private long mealId;

	@NotNull
	private long foodId;

	@NotNull
	private int foodAmount;
}

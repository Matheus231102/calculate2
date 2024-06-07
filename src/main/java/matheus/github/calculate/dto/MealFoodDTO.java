package matheus.github.calculate.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MealFoodDTO {

	@NotNull
	@Min(value = 0, message = "meal id must be positive")
	private Long mealId;

	@NotNull
	@Min(value = 0, message = "food id must be positive")
	private Long foodId;

	@NotNull
	@Min(value = 0, message = "food amount must be positive")
	private Integer foodAmount;
}

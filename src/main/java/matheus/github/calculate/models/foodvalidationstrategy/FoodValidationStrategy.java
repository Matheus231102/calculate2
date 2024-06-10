package matheus.github.calculate.models.foodvalidationstrategy;

import matheus.github.calculate.dto.FoodDTO;

@FunctionalInterface
public interface FoodValidationStrategy {
	void execute(FoodDTO foodDTO);
}

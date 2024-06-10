package matheus.github.calculate.models.foodvalidationstrategy.strategies;

import matheus.github.calculate.dto.FoodDTO;
import matheus.github.calculate.exception.exceptions.InvalidFoodException;
import matheus.github.calculate.models.foodvalidationstrategy.FoodValidationStrategy;
import matheus.github.calculate.utils.BigDecimalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class FoodPriceValidationStrategy implements FoodValidationStrategy {

	@Autowired
	private BigDecimalUtils bigDecimalUtils;

	@Override
	public void execute(FoodDTO foodDTO) {
		if (foodDTO.getPrice() == null) {
			foodDTO.setPrice(BigDecimal.ZERO);
		}

		if (bigDecimalUtils.isLessThan(foodDTO.getPrice(), BigDecimal.ZERO)) {
			throw new InvalidFoodException("Price must be greater than zero");
		}

	}
}

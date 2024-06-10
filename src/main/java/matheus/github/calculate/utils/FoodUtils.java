package matheus.github.calculate.utils;

import matheus.github.calculate.models.Food;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FoodUtils {

	public Food updateFoodProperties(Map<String, Object> properties, Food food) {
		properties.forEach((key, value) -> {
			if (value != null) {
				try {
					if (value instanceof Number) {
						if (BeanUtils.getProperty(food, key) != null) {
							BeanUtils.setProperty(food, key, value);
						}
					} else if (value instanceof String) {
						BeanUtils.setProperty(food, key, value);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		return food;
	}

}

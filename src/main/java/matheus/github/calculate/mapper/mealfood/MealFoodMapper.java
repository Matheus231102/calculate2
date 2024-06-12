package matheus.github.calculate.mapper.mealfood;

import matheus.github.calculate.dto.MealFoodDTO;
import matheus.github.calculate.models.MealFood;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MealFoodMapper {

	@Autowired
	private ModelMapper modelMapper;

	public MealFoodDTO toDTO(MealFood mealFood) {
		return modelMapper.map(mealFood, MealFoodDTO.class);
	}

	public MealFood toEntity(MealFoodDTO mealFoodDTO) {
		return modelMapper.map(mealFoodDTO, MealFood.class);
	}

}
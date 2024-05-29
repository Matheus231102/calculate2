package matheus.github.calculate.mapper.meal;

import matheus.github.calculate.dto.MealDTO;
import matheus.github.calculate.models.Meal;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MealMapper {

	@Autowired
	private ModelMapper modelMapper;

	public MealDTO toDTO(Meal meal) {
		return modelMapper.map(meal, MealDTO.class);
	}

	public Meal toEntity(MealDTO mealDTO) {
		return modelMapper.map(mealDTO, Meal.class);
	}

}

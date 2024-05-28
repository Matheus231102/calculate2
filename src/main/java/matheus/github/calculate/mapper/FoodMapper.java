package matheus.github.calculate.mapper;

import matheus.github.calculate.dto.FoodDTO;
import matheus.github.calculate.models.Food;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FoodMapper {

	@Autowired
	private ModelMapper modelMapper;

	public FoodDTO toDTO(Food food) {
		return modelMapper.map(food, FoodDTO.class);
	}

	public Food toEntity(FoodDTO foodDTO) {
		return modelMapper.map(foodDTO, Food.class);
	}

}

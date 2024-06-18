package matheus.github.calculate.services;

import matheus.github.calculate.dto.FoodDTO;
import matheus.github.calculate.exception.exceptions.user.FoodNotFoundException;
import matheus.github.calculate.exception.exceptions.user.UserNotFoundException;
import matheus.github.calculate.mapper.food.FoodMapper;
import matheus.github.calculate.models.Food;
import matheus.github.calculate.models.User;
import matheus.github.calculate.repositories.FoodRepository;
import matheus.github.calculate.utils.FoodUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class FoodService {

	@Autowired
	private FoodRepository foodRepository;

	@Autowired
	private FoodMapper foodMapper;

	@Autowired
	private UserService userService;

	@Autowired
	private FoodUtils foodUtils;

	public Food addFood(String authenticatedName, FoodDTO foodDTO) throws UserNotFoundException {
		User user = userService.getUser(authenticatedName);
		Food food = foodMapper.toEntity(foodDTO);
		registerFoodToUser(food, user);
		return foodRepository.save(food);
	}

	public List<Food> addFood(String authenticatedName, List<FoodDTO> foodDTOList) throws UserNotFoundException {
		User user = userService.getUser(authenticatedName);
		List<Food> foodList = new ArrayList<>();

		for (FoodDTO foodDTO : foodDTOList) {
			Food food = foodMapper.toEntity(foodDTO);
			foodList.add(food);
			registerFoodToUser(food, user);
		}

		return foodRepository.saveAll(foodList);
	}

	public Food updateFood(String authenticatedName, Map<String, Object> properties, Long id) throws UserNotFoundException {
		Food food = getFood(authenticatedName, id);

		Food updatedFood = foodUtils.updateFoodProperties(properties, food);
		return foodRepository.save(updatedFood);
	}

	public List<Food> getAllFood(String authenticatedUsername) throws UserNotFoundException {
		User user = userService.getUser(authenticatedUsername);
		return foodRepository.findAllByUser(user);
	}

	public Food getFood(String authenticatedUsername, long id) throws UserNotFoundException {
		User user = userService.getUser(authenticatedUsername);
		Optional<Food> foodOptional = foodRepository.findByUserAndId(user, id);

		if (foodOptional.isEmpty()) {
			throw new FoodNotFoundException(String.format("Food not found by provided id: %s", id));
		}

		return foodOptional.get();
	}

	public void deleteAllFood(String authenticatedUsername) throws UserNotFoundException {
		User user = userService.getUser(authenticatedUsername);
		foodRepository.deleteAllByUser(user);
	}

	public void deleteFood(String authenticatedUsername, long id) throws UserNotFoundException {
		User user = userService.getUser(authenticatedUsername);
		boolean foodExists = foodRepository.existsByUserAndId(user, id);
		if (!foodExists) {
			throw new FoodNotFoundException(String.format("Food not found by provided id: %s", id));
		}

		foodRepository.deleteByUserAndFoodId(user, id);
	}

	private void registerFoodToUser(Food food, User user) {
		user.getFoods().add(food);
		food.setUser(user);
	}
}

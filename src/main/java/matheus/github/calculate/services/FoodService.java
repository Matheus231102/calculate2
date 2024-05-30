package matheus.github.calculate.services;

import matheus.github.calculate.dto.FoodDTO;
import matheus.github.calculate.exception.exceptions.FoodNotFoundException;
import matheus.github.calculate.exception.exceptions.UserNotFoundException;
import matheus.github.calculate.mapper.food.FoodMapper;
import matheus.github.calculate.mapper.meal.MealMapper;
import matheus.github.calculate.models.Food;
import matheus.github.calculate.models.Meal;
import matheus.github.calculate.models.MealFood;
import matheus.github.calculate.models.User;
import matheus.github.calculate.repositories.FoodRepository;
import matheus.github.calculate.repositories.MealFoodRepository;
import matheus.github.calculate.repositories.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
	private MealFoodService mealFoodService;

	@Autowired
	private MealService mealService;

	@Autowired
	private MealFoodRepository mealFoodRepository;

	private void registerFoodToUser(Food food, User user) {
		user.getFoods().add(food);
		food.setUser(user);
	}

	public Food registerByAuthUsername(String authenticatedName, FoodDTO foodDTO) throws UserNotFoundException {
		User user = userService.findByUsername(authenticatedName);
		Food food = foodMapper.toEntity(foodDTO);
		registerFoodToUser(food, user);
		return foodRepository.save(food);
	}

	public Food register(Food food) {
		return foodRepository.save(food);
	}

	public List<Food> getAllFoodsByAuthUsername(String authenticatedUsername) throws UserNotFoundException {
		User user = userService.findByUsername(authenticatedUsername);
		return foodRepository.findAllByUser(user);
	}

	public Food getFoodByAuthUsernameAndFoodId(String authenticatedUsername, long id) throws UserNotFoundException {
		User user = userService.findByUsername(authenticatedUsername);
		Optional<Food> foodOptional = foodRepository.findByUserAndId(user, id);
		if (foodOptional.isEmpty()) {
			throw new FoodNotFoundException(String.format("Food not found by provided id: %s", id));
		}
		return foodOptional.get();
	}

	public void deleteAllFoodsByAuthUsername(String authenticatedUsername) throws UserNotFoundException {
		User user = userService.findByUsername(authenticatedUsername);
		foodRepository.deleteAllByUser(user);
	}

	public void deleteFoodByAuthUsernameAndFoodId(String authenticatedUsername, long id) throws UserNotFoundException {
		User user = userService.findByUsername(authenticatedUsername);
		boolean foodExists = foodRepository.existsByUserAndId(user, id);
		if (!foodExists) {
			throw new FoodNotFoundException(String.format("Food not found by provided id: %s", id));
		}
		foodRepository.deleteByUserAndId(user, id);
	}


}

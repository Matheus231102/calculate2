package matheus.github.calculate.services;

import matheus.github.calculate.dto.FoodDTO;
import matheus.github.calculate.dto.UserDTO;
import matheus.github.calculate.exception.exceptions.UserNotFoundException;
import matheus.github.calculate.mapper.FoodMapper;
import matheus.github.calculate.models.Food;
import matheus.github.calculate.models.User;
import matheus.github.calculate.repositories.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService {

	@Autowired
	private FoodRepository foodRepository;

	@Autowired
	private FoodMapper foodMapper;

	@Autowired
	private UserService userService;

	private void registerFoodToUser(Food food, User user) {
		user.getFoods().add(food);
		food.setUser(user);
	}

	public Food registerByAuthenticatedName(String authenticatedName, FoodDTO foodDTO) throws UserNotFoundException {
		User user = findUserByAuthenticatedName(authenticatedName);
		Food food = foodMapper.toEntity(foodDTO);
		registerFoodToUser(food, user);
		return foodRepository.save(food);
	}

	public List<Food> getAllFoodsByAuthenticatedName(String authenticatedUsername) throws UserNotFoundException {
		User user = findUserByAuthenticatedName(authenticatedUsername);
		return foodRepository.findAllByUser(user);
	}

	private User findUserByAuthenticatedName(String authenticatedName) throws UserNotFoundException {
		return userService.findByUsername(authenticatedName);
	}

}

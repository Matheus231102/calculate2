package matheus.github.calculate.services;

import matheus.github.calculate.dto.MealDTO;
import matheus.github.calculate.exception.exceptions.MealNotFoundException;
import matheus.github.calculate.exception.exceptions.UserNotFoundException;
import matheus.github.calculate.mapper.meal.MealMapper;
import matheus.github.calculate.models.Meal;
import matheus.github.calculate.models.User;
import matheus.github.calculate.repositories.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MealService {

	@Autowired
	private UserService userService;

	@Autowired
	private MealMapper mealMapper;

	@Autowired
	private MealRepository mealRepository;

	private void registerMealToUser(Meal meal, User user) {
		user.getMeals().add(meal);
		meal.setUser(user);
	}

	public Meal registerByAuthenticatedName(String authenticatedName, MealDTO mealDTO) throws UserNotFoundException {
		User user = userService.findByUsername(authenticatedName);
		Meal meal = mealMapper.toEntity(mealDTO);
		registerMealToUser(meal, user);
		return mealRepository.save(meal);
	}

	public Meal register(Meal meal) {
		return mealRepository.save(meal);
	}

	public List<Meal> getAllMealsByAuthenticatedName(String authenticatedName) throws UserNotFoundException {
		User user = userService.findByUsername(authenticatedName);
		return mealRepository.findAllByUser(user);
	}

	public Meal getMealByAuthUsernameAndMealId(String authenticatedName, long id) throws UserNotFoundException {
		User user = userService.findByUsername(authenticatedName);
		Optional<Meal> optionalMeal = mealRepository.findByUserAndId(user, id);
		if (optionalMeal.isEmpty()) {
			throw new MealNotFoundException(String.format("Meal not found by provided id: %s", id));
		}
		return optionalMeal.get();
	}
}

package matheus.github.calculate.services;

import matheus.github.calculate.dto.MealFoodDTO;
import matheus.github.calculate.exception.exceptions.user.UserNotFoundException;
import matheus.github.calculate.models.*;
import matheus.github.calculate.repositories.MealFoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MealFoodService {

	@Autowired
	private MealFoodRepository mealFoodRepository;

	@Lazy
	@Autowired
	private FoodService foodService;

	@Autowired
	private MealService mealService;

	@Autowired
	private UserService userService;


	public MealFood addMealFood(String authenticatedUsername, MealFoodDTO mealFoodDTO) throws UserNotFoundException {
		long foodId = mealFoodDTO.getFoodId();
		long mealId = mealFoodDTO.getMealId();

		Food food = getFood(authenticatedUsername, foodId);
		Meal meal = getMeal(authenticatedUsername, mealId);

		MealFood mealFood = createMealFood(mealFoodDTO, meal, food);

		return mealFoodRepository.save(mealFood);
	}

	public void deleteMealFood(String authenticatedUsername, long foodId) throws UserNotFoundException {
		// vericar existencia
		User user = userService.getUser(authenticatedUsername);
		mealFoodRepository.deleteAllByUserAndFoodId(user, foodId);
	}

	public void deleteMealFood(String authenticatedUsername, long mealid, long foodid) throws UserNotFoundException {
		// vericar existencia
		User user = userService.getUser(authenticatedUsername);
		mealFoodRepository.deleteAllByUserAndMealIdAndFoodId(user, mealid, foodid);
	}

	public List<MealFood> getAllMealFood(String authenticatedUsername) throws UserNotFoundException {
		User user = userService.getUser(authenticatedUsername);
		return mealFoodRepository.findAllByUser(user);
	}

	public Food getFood(String authenticatedUsername, long foodId) throws UserNotFoundException {
		return foodService.getFood(authenticatedUsername, foodId);
	}

	public Meal getMeal(String authenticatedUsername, long mealId) throws UserNotFoundException {
		return mealService.getMeal(authenticatedUsername, mealId);
	}

	public MealFood createMealFood(MealFoodDTO mealFoodDTO, Meal meal, Food food) {
		MealFoodId mealFoodId = new MealFoodId(mealFoodDTO.getMealId(), mealFoodDTO.getFoodId());
		MealFood mealFood = new MealFood();

		mealFood.setId(mealFoodId);
		mealFood.setFood(food);
		mealFood.setMeal(meal);
		mealFood.setFoodAmount(mealFoodDTO.getFoodAmount());

		meal.getMealFoods().add(mealFood);
		food.getMealFoods().add(mealFood);

		return mealFood;
	}

}

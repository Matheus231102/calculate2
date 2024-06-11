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


	public MealFood registerMealFoodByUser(String authenticatedUsername, MealFoodDTO mealFoodDTO) throws UserNotFoundException {
		long foodId = mealFoodDTO.getFoodId();
		long mealId = mealFoodDTO.getMealId();

		Food food = getFoodByUser(authenticatedUsername, foodId);
		Meal meal = getMealByUser(authenticatedUsername, mealId);

		MealFood mealFood = createMealFood(mealFoodDTO, meal, food);

		return mealFoodRepository.save(mealFood);
	}

	public void deleteMealFoodByUserAndFoodId(String authenticatedName,long foodId) {
		mealFoodRepository.deleteAllByUserAndFoodId(authenticatedName, foodId);
	}

	public List<MealFood> getAllMealFoodByUser(String authenticatedName) throws UserNotFoundException {
		User user = userService.findByUsername(authenticatedName);
		return mealFoodRepository.findAllByUser(user);
	}

	public Food getFoodByUser(String authenticatedUsername, long foodId) throws UserNotFoundException {
		return foodService.getFoodByUserAndFoodId(authenticatedUsername, foodId);
	}

	public Meal getMealByUser(String authenticatedUsername, long mealId) throws UserNotFoundException {
		return mealService.getMealByAuthUsernameAndMealId(authenticatedUsername, mealId);
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

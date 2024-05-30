package matheus.github.calculate.services;

import matheus.github.calculate.exception.exceptions.UserNotFoundException;
import matheus.github.calculate.models.Food;
import matheus.github.calculate.models.Meal;
import matheus.github.calculate.models.MealFood;
import matheus.github.calculate.models.User;
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


	public MealFood registerMealFoodByAuthUsername(String authenticatedUsername, long foodId, long mealId, int foodAmount) throws UserNotFoundException {
		Food food = foodService.getFoodByAuthUsernameAndFoodId(authenticatedUsername, foodId);
		Meal meal = mealService.getMealByAuthUsernameAndMealId(authenticatedUsername, mealId);

		MealFood mealFood = new MealFood();
		mealFood.setFoodAmount(foodAmount);

		meal.getMealFoods().add(mealFood);
		food.getMealFoods().add(mealFood);

		mealFood.setFood(food);
		mealFood.setMeal(meal);

		foodService.register(food);
		mealService.register(meal);

		return mealFoodRepository.save(mealFood);
	}

	public List<MealFood> getAllMealFoodByAuthUsername(String authenticatedName) throws UserNotFoundException {
		User user = userService.findByUsername(authenticatedName);
		return mealFoodRepository.findAllByUser(user);
	}

}

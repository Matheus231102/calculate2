package matheus.github.calculate.services;

import matheus.github.calculate.dto.MealFoodDTO;
import matheus.github.calculate.exception.exceptions.MealFoodNotFoundException;
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

	public void deleteMealFoodsWithFoodsId(String authenticatedUsername, List<Long> foodsId) throws UserNotFoundException {
		User user = userService.getUser(authenticatedUsername);
		foodsId.forEach(foodId -> {
			mealFoodRepository.deleteAllByUserAndFoodId(user, foodId);
		});
	}

	public void deleteMealFoodWithFoodId(String authenticatedUsername, Long foodId) throws UserNotFoundException {
		User user = userService.getUser(authenticatedUsername);
		mealFoodRepository.deleteAllByUserAndFoodId(user, foodId);
	}

	public void deleteMealFoodWithMealId(String authenticatedUsername, Long mealid) throws UserNotFoundException {
		User user = userService.getUser(authenticatedUsername);
		mealFoodRepository.deleteAllByUserAndMealId(user, mealid);
	}

	public void deleteMealFood(String authenticatedUsername, Long mealid, Long foodid) throws UserNotFoundException {
		User user = userService.getUser(authenticatedUsername);
		mealFoodRepository.deleteAllByUserAndMealIdAndFoodId(user, mealid, foodid);
	}

	public List<MealFood> getAllMealFood(String authenticatedUsername) throws UserNotFoundException {
		User user = userService.getUser(authenticatedUsername);
		return mealFoodRepository.findAllByUser(user);
	}

	public MealFood updateMealFood(String authenticatedUsername, MealFoodDTO mealFoodDTO) throws UserNotFoundException, MealFoodNotFoundException {
		User user = userService.getUser(authenticatedUsername);
		Food food = getFood(authenticatedUsername, mealFoodDTO.getFoodId());
		Meal meal = getMeal(authenticatedUsername, mealFoodDTO.getMealId());
		int foodAmount = mealFoodDTO.getFoodAmount();

		boolean exists = mealFoodRepository.existsByUserAndMealAndFood(user, meal, food);
		if (!exists) {
			throw new MealFoodNotFoundException(String.format("MealFood not found by provided Meal id: %s and Food id: %s", meal.getId(), food.getId()));
		}

		MealFoodId mealFoodId = new MealFoodId(mealFoodDTO.getMealId(), mealFoodDTO.getFoodId());

		MealFood mealFood = mealFoodRepository.findById(mealFoodId);
		mealFood.setFoodAmount(foodAmount);

		return mealFoodRepository.save(mealFood);
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

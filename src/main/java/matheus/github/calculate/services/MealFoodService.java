package matheus.github.calculate.services;

import matheus.github.calculate.models.Food;
import matheus.github.calculate.models.Meal;
import matheus.github.calculate.models.MealFood;
import matheus.github.calculate.repositories.MealFoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MealFoodService {

	@Autowired
	private MealFoodRepository mealFoodRepository;

	public MealFood registerMealFood(Meal meal, Food food) {
		MealFood mealFood = new MealFood();
		mealFood.setFood(food);
		mealFood.setMeal(meal);
		return mealFoodRepository.save(mealFood);
	}

}

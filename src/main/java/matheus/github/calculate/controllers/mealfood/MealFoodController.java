package matheus.github.calculate.controllers.mealfood;

import matheus.github.calculate.controllers.paths.PathConstants;
import matheus.github.calculate.dto.RelationshipMealFoodAmountDTO;
import matheus.github.calculate.exception.exceptions.UserNotFoundException;
import matheus.github.calculate.models.MealFood;
import matheus.github.calculate.security.AuthenticationContext;
import matheus.github.calculate.services.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(PathConstants.DEFAULT_MEALFOOD_PATH)
public class MealFoodController {

	@Autowired
	private AuthenticationContext authenticationContext;

	@Autowired
	private FoodService foodService;

	@PostMapping("/mealfood")
	public ResponseEntity<MealFood> insertFoodIntoMeal(@RequestBody RelationshipMealFoodAmountDTO receivedObject) throws UserNotFoundException {
		long fooid = receivedObject.getFoodId();
		long mealid = receivedObject.getMealId();
		int foodAmount = receivedObject.getFoodAmount();

		String authenticatedUsername = authenticationContext.getAuthenticatedUsername();
		MealFood mealFood = foodService.insertFoodIntoMeal(authenticatedUsername, fooid, mealid, foodAmount);
		return ResponseEntity.ok(mealFood);
	}

}

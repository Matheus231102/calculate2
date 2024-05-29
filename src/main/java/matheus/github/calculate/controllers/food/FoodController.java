package matheus.github.calculate.controllers.food;

import jakarta.validation.Valid;
import matheus.github.calculate.controllers.paths.PathConstants;
import matheus.github.calculate.dto.FoodDTO;
import matheus.github.calculate.exception.exceptions.UserNotFoundException;
import matheus.github.calculate.models.Food;
import matheus.github.calculate.security.AuthenticationContext;
import matheus.github.calculate.services.FoodService;
import matheus.github.calculate.services.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(PathConstants.DEFAULT_FOOD_PATH)
public class FoodController {

	@Autowired
	private AuthenticationContext authenticationContext;

	@Autowired
	private FoodService foodService;

	@Autowired
	private MealService mealService;

	@PostMapping
	public ResponseEntity<Food> registerFood(@RequestBody @Valid FoodDTO foodDTO) throws UserNotFoundException {
		String authenticatedUsername = authenticationContext.getAuthenticatedUsername();
		Food food = foodService.registerByAuthUsername(authenticatedUsername, foodDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(food);
	}

	@GetMapping
	public ResponseEntity<List<Food>> getAllFoods() throws UserNotFoundException {
		String authenticatedUsername = authenticationContext.getAuthenticatedUsername();
		List<Food> foodList = foodService.getAllFoodsByAuthUsername(authenticatedUsername);
		return ResponseEntity.ok(foodList);
	}

	@DeleteMapping
	public ResponseEntity deleteAllFoods() throws UserNotFoundException {
		String authenticatedUsername = authenticationContext.getAuthenticatedUsername();
		foodService.deleteAllFoodsByAuthUsername(authenticatedUsername);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity deleteFood(@PathVariable long id) throws UserNotFoundException {
		String authenticatedUsername = authenticationContext.getAuthenticatedUsername();
		foodService.deleteFoodByAuthUsernameAndFoodId(authenticatedUsername, id);
		return ResponseEntity.noContent().build();
	}

	// Recebe foodId do usuario, Recebe mealId do usuario e acrecenta o relacionamento entre food e meal
	//todo insertFoodInMeal
}

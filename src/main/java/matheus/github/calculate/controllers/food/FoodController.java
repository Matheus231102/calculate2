package matheus.github.calculate.controllers.food;

import jakarta.validation.Valid;
import matheus.github.calculate.dto.FoodDTO;
import matheus.github.calculate.exception.exceptions.user.UserNotFoundException;
import matheus.github.calculate.models.Food;
import matheus.github.calculate.models.foodvalidationstrategy.FoodValidationStrategy;
import matheus.github.calculate.paths.PathConstants;
import matheus.github.calculate.security.AuthenticationContext;
import matheus.github.calculate.services.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(PathConstants.DEFAULT_FOOD_PATH)
public class FoodController {

	@Autowired
	private AuthenticationContext authenticationContext;

	@Autowired
	private FoodService foodService;

	@Autowired
	private List<FoodValidationStrategy> foodValidationStrategies;

	@PostMapping
	public ResponseEntity<Food> registerFood(@RequestBody @Valid FoodDTO foodDTO) throws UserNotFoundException {
		foodValidationStrategies.forEach(foodValidationStrategy -> foodValidationStrategy.execute(foodDTO));

		String authenticatedUsername = authenticationContext.getAuthenticatedUsername();
		Food food = foodService.registerFoodByUser(authenticatedUsername, foodDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(food);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Food> updateFood(@RequestBody Map<String, Object> fields , @PathVariable Long id) throws UserNotFoundException {
		String authenticatedUsername = authenticationContext.getAuthenticatedUsername();
		Food food = foodService.updateFoodByUser(authenticatedUsername, fields, id);

		return ResponseEntity.ok(food);
	}

	@GetMapping
	public ResponseEntity<List<Food>> getAllFoods() throws UserNotFoundException {
		String authenticatedUsername = authenticationContext.getAuthenticatedUsername();
		List<Food> foodList = foodService.getAllFoodsByUser(authenticatedUsername);
		return ResponseEntity.ok(foodList);
	}

	@DeleteMapping
	public ResponseEntity<Void> deleteAllFoods() throws UserNotFoundException {
		String authenticatedUsername = authenticationContext.getAuthenticatedUsername();
		foodService.deleteAllFoodsByUser(authenticatedUsername);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteFood(@PathVariable long id) throws UserNotFoundException {
		String authenticatedUsername = authenticationContext.getAuthenticatedUsername();
		foodService.deleteFoodByUserAndFoodId(authenticatedUsername, id);
		return ResponseEntity.noContent().build();
	}

}

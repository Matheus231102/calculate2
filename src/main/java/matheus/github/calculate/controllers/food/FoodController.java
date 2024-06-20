package matheus.github.calculate.controllers.food;

import jakarta.validation.Valid;
import matheus.github.calculate.dto.FoodDTO;
import matheus.github.calculate.exception.exceptions.user.UserNotFoundException;
import matheus.github.calculate.models.Food;
import matheus.github.calculate.models.foodvalidationstrategy.FoodValidationStrategy;
import matheus.github.calculate.paths.PathConstants;
import matheus.github.calculate.security.AuthenticationContext;
import matheus.github.calculate.services.FoodService;
import matheus.github.calculate.services.MealFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(PathConstants.DEFAULT_FOOD_PATH)
@PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MANAGER')")
public class FoodController {

	@Autowired
	private AuthenticationContext authenticationContext;

	@Autowired
	private FoodService foodService;

	@Autowired
	private List<FoodValidationStrategy> foodValidationStrategies;

	@Autowired
	private MealFoodService mealFoodService;

	@PostMapping
	public ResponseEntity<Food> addFood(@RequestBody @Valid FoodDTO foodDTO) throws UserNotFoundException {
		foodValidationStrategies.forEach(foodValidationStrategy -> foodValidationStrategy.execute(foodDTO));

		String authenticatedUsername = authenticationContext.getAuthenticatedUsername();
		Food food = foodService.addFood(authenticatedUsername, foodDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(food);
	}

	@PostMapping("/list")
	public ResponseEntity<List<Food>> addFood(@RequestBody @Valid List<FoodDTO> foodDTOList) throws UserNotFoundException {
		foodDTOList.forEach(foodDto -> {
			foodValidationStrategies.forEach(foodValidationStrategy -> foodValidationStrategy.execute(foodDto));
		});

		String authenticatedUsername = authenticationContext.getAuthenticatedUsername();
		List<Food> foodList = foodService.addFood(authenticatedUsername, foodDTOList);
		return ResponseEntity.status(HttpStatus.CREATED).body(foodList);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Food> updateFood(@RequestBody Map<String, Object> fields , @PathVariable Long id) throws UserNotFoundException {
		String authenticatedUsername = authenticationContext.getAuthenticatedUsername();
		Food food = foodService.updateFood(authenticatedUsername, fields, id);
		return ResponseEntity.ok(food);
	}

	@GetMapping
	public ResponseEntity<List<Food>> getAllFood() throws UserNotFoundException {
		String authenticatedUsername = authenticationContext.getAuthenticatedUsername();
		List<Food> foodList = foodService.getAllFood(authenticatedUsername);
		return ResponseEntity.ok(foodList);
	}

	@DeleteMapping
	public ResponseEntity<Void> deleteAllFood() throws UserNotFoundException {
		String authenticatedUsername = authenticationContext.getAuthenticatedUsername();
		foodService.deleteAllFood(authenticatedUsername);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{fooid}")
	public ResponseEntity<Void> deleteFood(@PathVariable long fooid) throws UserNotFoundException {
		String authenticatedUsername = authenticationContext.getAuthenticatedUsername();
		mealFoodService.deleteMealFoodWithFoodId(authenticatedUsername, fooid);
		foodService.deleteFood(authenticatedUsername, fooid);
		return ResponseEntity.noContent().build();
	}

}

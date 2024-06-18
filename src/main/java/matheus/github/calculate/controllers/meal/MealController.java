package matheus.github.calculate.controllers.meal;

import jakarta.validation.Valid;
import matheus.github.calculate.dto.MealDTO;
import matheus.github.calculate.exception.exceptions.user.UserNotFoundException;
import matheus.github.calculate.models.Meal;
import matheus.github.calculate.security.AuthenticationContext;
import matheus.github.calculate.services.MealFoodService;
import matheus.github.calculate.services.MealService;
import matheus.github.calculate.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static matheus.github.calculate.paths.PathConstants.DEFAULT_MEAL_PATH;

@RestController
@RequestMapping(DEFAULT_MEAL_PATH)
public class MealController {

	@Autowired
	private MealService mealService;

	@Autowired
	private AuthenticationContext authenticationContext;

	@Autowired
	private MealFoodService mealFoodService;

	@PostMapping
	public ResponseEntity<Meal> addMeal(@RequestBody @Valid MealDTO mealDTO) throws UserNotFoundException {
		String authenticatedUsername = authenticationContext.getAuthenticatedUsername();
		Meal meal = mealService.addMeal(authenticatedUsername, mealDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(meal);
	}

	@GetMapping
	public ResponseEntity<List<Meal>> getAllMeal() throws UserNotFoundException {
		String authenticatedUsername = authenticationContext.getAuthenticatedUsername();
		List<Meal> mealList = mealService.getAllMeal(authenticatedUsername);
		return ResponseEntity.status(HttpStatus.OK).body(mealList);
	}

	@DeleteMapping("/{mealid}")
	public ResponseEntity<Void> deleteMeal(@PathVariable("mealid") long mealid) throws UserNotFoundException {
		String authenticatedUsername = authenticationContext.getAuthenticatedUsername();
		mealFoodService.deleteMealFoodWithMealId(authenticatedUsername, mealid);
		mealService.deleteMeal(authenticatedUsername, mealid);
		return ResponseEntity.noContent().build();
	}

}

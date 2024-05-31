package matheus.github.calculate.controllers.meal;

import jakarta.validation.Valid;
import matheus.github.calculate.dto.MealDTO;
import matheus.github.calculate.exception.exceptions.user.UserNotFoundException;
import matheus.github.calculate.models.Meal;
import matheus.github.calculate.security.AuthenticationContext;
import matheus.github.calculate.services.MealService;
import matheus.github.calculate.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static matheus.github.calculate.controllers.paths.PathConstants.DEFAULT_MEAL_PATH;

@RestController
@RequestMapping(DEFAULT_MEAL_PATH)
public class MealController {

	@Autowired
	private MealService mealService;

	@Autowired
	private AuthenticationContext authenticationContext;

	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity<Meal> registerMeal(@RequestBody @Valid MealDTO mealDTO) throws UserNotFoundException {
		String authenticatedUsername = authenticationContext.getAuthenticatedUsername();
		Meal meal = mealService.registerByAuthenticatedName(authenticatedUsername, mealDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(meal);
	}

	@GetMapping
	public ResponseEntity<List<Meal>> getAllMeals() throws UserNotFoundException {
		String authenticatedUsername = authenticationContext.getAuthenticatedUsername();
		List<Meal> mealList = mealService.getAllMealsByAuthenticatedName(authenticatedUsername);
		return ResponseEntity.status(HttpStatus.OK).body(mealList);
	}

}

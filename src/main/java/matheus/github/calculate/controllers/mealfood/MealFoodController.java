package matheus.github.calculate.controllers.mealfood;

import jakarta.validation.Valid;
import matheus.github.calculate.paths.PathConstants;
import matheus.github.calculate.dto.MealFoodDTO;
import matheus.github.calculate.exception.exceptions.user.UserNotFoundException;
import matheus.github.calculate.models.MealFood;
import matheus.github.calculate.security.AuthenticationContext;
import matheus.github.calculate.services.MealFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(PathConstants.DEFAULT_MEALFOOD_PATH)
public class MealFoodController {

	@Autowired
	private AuthenticationContext authenticationContext;

	@Autowired
	private MealFoodService mealFoodService;

	@GetMapping
	public ResponseEntity<List<MealFood>> getAllMealFoodsByAuthUsername() throws UserNotFoundException {
		String authenticatedUsername = authenticationContext.getAuthenticatedUsername();
		List<MealFood> mealFoodList = mealFoodService.getAllMealFoodByAuthUsername(authenticatedUsername);
		return ResponseEntity.ok(mealFoodList);
	}

	@PostMapping
	public ResponseEntity<MealFood> insertFoodIntoMealByAuthUsername(@RequestBody @Valid MealFoodDTO mealFoodDTO) throws UserNotFoundException {
		String authenticatedUsername = authenticationContext.getAuthenticatedUsername();
		MealFood mealFood = mealFoodService.registerMealFoodByAuthUsername(authenticatedUsername, mealFoodDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(mealFood);
	}

}

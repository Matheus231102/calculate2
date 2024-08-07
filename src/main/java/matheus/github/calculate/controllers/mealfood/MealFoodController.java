package matheus.github.calculate.controllers.mealfood;

import jakarta.validation.Valid;
import matheus.github.calculate.exception.exceptions.MealFoodNotFoundException;
import matheus.github.calculate.paths.PathConstants;
import matheus.github.calculate.dto.MealFoodDTO;
import matheus.github.calculate.exception.exceptions.user.UserNotFoundException;
import matheus.github.calculate.models.MealFood;
import matheus.github.calculate.security.AuthenticationContext;
import matheus.github.calculate.services.MealFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(PathConstants.DEFAULT_MEALFOOD_PATH)
@PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MANAGER')")
public class MealFoodController {

	@Autowired
	private AuthenticationContext authenticationContext;

	@Autowired
	private MealFoodService mealFoodService;

	@GetMapping
	public ResponseEntity<List<MealFood>> getAllMealFood() throws UserNotFoundException {
		String authenticatedUsername = authenticationContext.getAuthenticatedUsername();
		List<MealFood> mealFoodList = mealFoodService.getAllMealFood(authenticatedUsername);
		return ResponseEntity.ok(mealFoodList);
	}

	@PostMapping
	public ResponseEntity<MealFood> addMealFood(@RequestBody @Valid MealFoodDTO mealFoodDTO) throws UserNotFoundException {
		String authenticatedUsername = authenticationContext.getAuthenticatedUsername();
		MealFood mealFood = mealFoodService.addMealFood(authenticatedUsername, mealFoodDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(mealFood);
	}

	@DeleteMapping
	public ResponseEntity<Void> deleteMealFood(@RequestParam("meal") long mealid, @RequestParam("food") long foodid) throws UserNotFoundException {
		String authenticatedUsername = authenticationContext.getAuthenticatedUsername();
		mealFoodService.deleteMealFood(authenticatedUsername, mealid, foodid);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping
	public ResponseEntity<MealFood> updateMealFood(@RequestBody @Valid MealFoodDTO mealFoodDTO) throws UserNotFoundException, MealFoodNotFoundException {
		String authenticatedUsername = authenticationContext.getAuthenticatedUsername();
		MealFood mealFood = mealFoodService.updateMealFood(authenticatedUsername, mealFoodDTO);
		return ResponseEntity.ok(mealFood);
	}

}

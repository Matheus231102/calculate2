package matheus.github.calculate.controllers.mealfood;

import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import matheus.github.calculate.controllers.paths.PathConstants;
import matheus.github.calculate.exception.exceptions.UserNotFoundException;
import matheus.github.calculate.models.MealFood;
import matheus.github.calculate.repositories.MealFoodRepository;
import matheus.github.calculate.security.AuthenticationContext;
import matheus.github.calculate.services.FoodService;
import matheus.github.calculate.services.MealFoodService;
import matheus.github.calculate.services.MealService;
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
	private FoodService foodService;

	@Autowired
	private MealFoodService mealFoodService;

	@Autowired
	private MealService mealService;

	@Autowired
	private MealFoodRepository mealFoodRepository;

	@GetMapping
	public ResponseEntity<List<MealFood>> getAllMealFoodsByAuthUsername() throws UserNotFoundException {
		String authenticatedUsername = authenticationContext.getAuthenticatedUsername();
		List<MealFood> mealFoodList = mealFoodService.getAllMealFoodByAuthUsername(authenticatedUsername);
		return ResponseEntity.ok(mealFoodList);
	}

	@PostMapping("/food/{foodid}/meal/{mealid}")
	public ResponseEntity<MealFood> insertFoodIntoMealByAuthUsername(@PathVariable long foodid, @PathVariable long mealid, @RequestParam int amount) throws UserNotFoundException {
		String authenticatedUsername = authenticationContext.getAuthenticatedUsername();
		MealFood mealFood = mealFoodService.registerMealFoodByAuthUsername(authenticatedUsername, foodid, mealid, amount);
		return ResponseEntity.status(HttpStatus.CREATED).body(mealFood);
	}

}

package matheus.github.calculate.controllers.food;

import jakarta.validation.Valid;
import matheus.github.calculate.controllers.paths.PathConstants;
import matheus.github.calculate.dto.FoodDTO;
import matheus.github.calculate.exception.exceptions.UserNotFoundException;
import matheus.github.calculate.models.Food;
import matheus.github.calculate.security.AuthenticationContext;
import matheus.github.calculate.services.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(PathConstants.DEFAULT_FOOD_PATH)
public class FoodController {

	@Autowired
	private AuthenticationContext authenticationContext;

	@Autowired
	private FoodService foodService;

	@PostMapping
	public Food registerFood(@RequestBody @Valid FoodDTO foodDTO) throws UserNotFoundException {
		String authenticatedUsername = authenticationContext.getAuthenticatedUsername();
		return foodService.registerByAuthenticatedName(authenticatedUsername, foodDTO);
	}

	@GetMapping
	public List<Food> getAllFoods() throws UserNotFoundException {
		String authenticatedUsername = authenticationContext.getAuthenticatedUsername();
		return foodService.getAllFoodsByAuthenticatedName(authenticatedUsername);
	}

	@DeleteMapping
	public void deleteAllFoods() throws UserNotFoundException {
		String authenticatedUsername = authenticationContext.getAuthenticatedUsername();
		foodService.deleteAllFoodsByAuthenticatedName(authenticatedUsername);
	}

	@DeleteMapping("/{id}")
	public void deleteFood(@PathVariable long id) throws UserNotFoundException {
		String authenticatedUsername = authenticationContext.getAuthenticatedUsername();
		foodService.deleteFoodByAuthenticatedNameAndFoodId(authenticatedUsername, id);
	}

}

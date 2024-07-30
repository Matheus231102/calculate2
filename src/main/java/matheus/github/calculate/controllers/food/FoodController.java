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

/**
 * Controlador que permite com que entidade (Food) seja gerenciada.
 */
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

	/**
	 *
	 * @param foodDTO Objeto que representa a entidade (Food).
	 * @return entidade persistida com valor de id não nulo.
	 * @throws UserNotFoundException caso usuário não esteja autenticado corretamente através do token JWT.
	 */
	@PostMapping
	public ResponseEntity<Food> addFood(@RequestBody @Valid FoodDTO foodDTO) throws UserNotFoundException {
		foodValidationStrategies.forEach(foodValidationStrategy -> foodValidationStrategy.execute(foodDTO));

		String authenticatedUsername = authenticationContext.getAuthenticatedUsername();
		Food food = foodService.addFood(authenticatedUsername, foodDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(food);
	}

	/**
	 *
	 * @param foodDTOList lista de objetos que representam a entidade (Food), objetos enviados podem ter valores iguais como (name).
	 * @return lista das entidades persistidas com valores de identificação (id) não nulo.
	 * @throws UserNotFoundException caso usuário não esteja autenticado corretamente através do token JWT.
	 */
	@PostMapping("/list")
	public ResponseEntity<List<Food>> addFood(@RequestBody @Valid List<FoodDTO> foodDTOList) throws UserNotFoundException {
		foodDTOList.forEach(foodDto -> {
			foodValidationStrategies.forEach(foodValidationStrategy -> foodValidationStrategy.execute(foodDto));
		});

		String authenticatedUsername = authenticationContext.getAuthenticatedUsername();
		List<Food> foodList = foodService.addFood(authenticatedUsername, foodDTOList);
		return ResponseEntity.status(HttpStatus.CREATED).body(foodList);
	}

	/**
	 *
	 * @param fields Campos necessários para atualização, não é necessário preenchimento de todos os valores obrigatórios da entidadade (FoodDTO) como (name, calories, proteins, carbohydrates, fats),
	 *                 deve ser utilizado pelo menos 1 atributo, exceto atributo de identificação (id).
	 * @param id id da entidade (Food) que deseja modificar.
	 * @return lista das entidades com atributos atualizados.
	 * @throws UserNotFoundException caso usuário não esteja autenticado corretamente através do token JWT.
	 */
	@PatchMapping("/{id}")
	public ResponseEntity<Food> updateFood(@RequestBody Map<String, Object> fields , @PathVariable Long id) throws UserNotFoundException {
		String authenticatedUsername = authenticationContext.getAuthenticatedUsername();
		Food food = foodService.updateFood(authenticatedUsername, fields, id);
		return ResponseEntity.ok(food);
	}

	/**
	 *
	 * @return lista de todas as entidades (Food) ligadas ao usuário autenticado.
	 * @throws UserNotFoundException caso usuário não esteja autenticado corretamente através do token JWT.
	 */
	@GetMapping
	public ResponseEntity<List<Food>> getAllFood() throws UserNotFoundException {
		String authenticatedUsername = authenticationContext.getAuthenticatedUsername();
		List<Food> foodList = foodService.getAllFood(authenticatedUsername);
		return ResponseEntity.ok(foodList);
	}

	/**
	 * Remove todas as entidades (Food) ligadas ao usuário autenticado.
	 * @return void
	 * @throws UserNotFoundException caso usuário não esteja autenticado corretamente através do token JWT.
	 */
	@DeleteMapping
	public ResponseEntity<Void> deleteAllFood() throws UserNotFoundException {
		String authenticatedUsername = authenticationContext.getAuthenticatedUsername();
		foodService.deleteAllFood(authenticatedUsername);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Remove entidade (Food) ligada ao usuário autenticado através de seu id.
	 * @param fooid id da entidade (Food) que deseja remover.
	 * @return void
	 * @throws UserNotFoundException caso usuário não esteja autenticado corretamente através do token JWT.
	 */
	@DeleteMapping("/{fooid}")
	public ResponseEntity<Void> deleteFood(@PathVariable long fooid) throws UserNotFoundException {
		String authenticatedUsername = authenticationContext.getAuthenticatedUsername();
		mealFoodService.deleteMealFoodWithFoodId(authenticatedUsername, fooid);
		foodService.deleteFood(authenticatedUsername, fooid);
		return ResponseEntity.noContent().build();
	}

}

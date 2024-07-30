package matheus.github.calculate.services;

import matheus.github.calculate.dto.FoodDTO;
import matheus.github.calculate.exception.exceptions.user.FoodNotFoundException;
import matheus.github.calculate.exception.exceptions.user.UserNotFoundException;
import matheus.github.calculate.mapper.food.FoodMapper;
import matheus.github.calculate.models.Food;
import matheus.github.calculate.models.User;
import matheus.github.calculate.repositories.FoodRepository;
import matheus.github.calculate.utils.FoodUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Serviço que permite com que entidade (Food) seja gerenciada.
 */
@Service
public class FoodService {

	@Autowired
	private FoodRepository foodRepository;

	@Autowired
	private FoodMapper foodMapper;

	@Autowired
	private UserService userService;

	@Autowired
	private FoodUtils foodUtils;

	/**
	 *
	 * @param authenticatedName nome do usuário autenticado, precisa ser capturado através do AuthenticationContext e enviado como parâmetro para a função.
	 * @param foodDTO Objeto que representa a entidade (Food).
	 * @return entidade Food com identificação (id) não nulo.
	 * @throws UserNotFoundException caso usuário não esteja autenticado corretamente através do token JWT.
	 */
	public Food addFood(String authenticatedName, FoodDTO foodDTO) throws UserNotFoundException {
		User user = userService.getUser(authenticatedName);
		Food food = foodMapper.toEntity(foodDTO);
		registerFoodToUser(food, user);
		return foodRepository.save(food);
	}

	/**
	 *
	 * @param authenticatedName nome do usuário autenticado, precisa ser capturado através do AuthenticationContext e enviado como parâmetro para a função.
	 * @param foodDTOList lista de objetos que representam a entidade (Food).
	 * @return lista das entidades persistidas com valores de identificação (id) não nulo.
	 * @throws UserNotFoundException caso usuário não esteja autenticado corretamente através do token JWT.
	 */
	public List<Food> addFood(String authenticatedName, List<FoodDTO> foodDTOList) throws UserNotFoundException {
		User user = userService.getUser(authenticatedName);
		List<Food> foodList = new ArrayList<>();

		for (FoodDTO foodDTO : foodDTOList) {
			Food food = foodMapper.toEntity(foodDTO);
			foodList.add(food);
			registerFoodToUser(food, user);
		}

		return foodRepository.saveAll(foodList);
	}

	/**
	 *
	 * @param authenticatedName nome do usuário autenticado, precisa ser capturado através do AuthenticationContext e enviado como parâmetro para a função.
	 * @param properties Campos necessários para atualização, não é necessário preenchimento de todos os valores obrigatórios da entidadade (FoodDTO) como (name, calories, proteins, carbohydrates, fats),
	 * 	 *                 deve ser utilizado pelo menos 1 atributo, exceto atributo de identificação (id).
	 * @param id id da entidade (Food) que deseja modificar.
	 * @return entidade com atributos atualizados.
	 * @throws UserNotFoundException caso usuário não esteja autenticado corretamente através do token JWT.
	 */
	public Food updateFood(String authenticatedName, Map<String, Object> properties, Long id) throws UserNotFoundException {
		Food food = getFood(authenticatedName, id);

		Food updatedFood = foodUtils.updateFoodProperties(properties, food);
		return foodRepository.save(updatedFood);
	}

	/**
	 *
	 * @param authenticatedUsername nome do usuário autenticado, precisa ser capturado através do AuthenticationContext e enviado como parâmetro para a função.
	 * @return lista de todas as entidades (Food) ligadas ao usuário autenticado.
	 * @throws UserNotFoundException caso usuário não esteja autenticado corretamente através do token JWT.
	 */
	public List<Food> getAllFood(String authenticatedUsername) throws UserNotFoundException {
		User user = userService.getUser(authenticatedUsername);
		return foodRepository.findAllByUser(user);
	}

	/**
	 * @param authenticatedUsername nome do usuário autenticado, precisa ser capturado através do AuthenticationContext e enviado como parâmetro para a função.
	 * @param id id da entidade (Food).
	 * @return entidade referente ao id enviado e ao usuário autenticado.
	 * @throws UserNotFoundException caso usuário não esteja autenticado corretamente através do token JWT.
	 * @throws FoodNotFoundException caso não exista uma entidade com a identificação enviado para o usuário autenticado.
	 */
	public Food getFood(String authenticatedUsername, long id) throws UserNotFoundException, FoodNotFoundException {
		User user = userService.getUser(authenticatedUsername);
		Optional<Food> foodOptional = foodRepository.findByUserAndId(user, id);

		if (foodOptional.isEmpty()) {
			throw new FoodNotFoundException(String.format("Food not found by provided id: %s", id));
		}

		return foodOptional.get();
	}

	/**
	 * Remove todas as entidades (Food) ligadas ao usuário autenticado.
	 * @param authenticatedUsername nome do usuário autenticado, precisa ser capturado através do AuthenticationContext e enviado como parâmetro para a função.
	 * @throws UserNotFoundException caso usuário não esteja autenticado corretamente através do token JWT.
	 */
	public void deleteAllFood(String authenticatedUsername) throws UserNotFoundException {
		User user = userService.getUser(authenticatedUsername);
		foodRepository.deleteAllByUser(user);
	}

	/**
	 *
	 * @param authenticatedUsername nome do usuário autenticado, precisa ser capturado através do AuthenticationContext e enviado como parâmetro para a função.
	 * @param id id da entidade (Food) a ser removida.
	 * @throws UserNotFoundException caso usuário não esteja autenticado corretamente através do token JWT.
	 */
	public void deleteFood(String authenticatedUsername, long id) throws UserNotFoundException {
		User user = userService.getUser(authenticatedUsername);
		boolean foodExists = foodRepository.existsByUserAndId(user, id);
		if (!foodExists) {
			throw new FoodNotFoundException(String.format("Food not found by provided id: %s", id));
		}

		foodRepository.deleteByUserAndFoodId(user, id);
	}

	/**
	 * Registra a entidade (Food) a uma entidade (User) para evitar conflitos nos dados.
	 * @param food
	 * @param user
	 */
	private void registerFoodToUser(Food food, User user) {
		user.getFoods().add(food);
		food.setUser(user);
	}
}

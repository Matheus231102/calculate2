package matheus.github.calculate.models.uservalidationstrategy.strategies;

import matheus.github.calculate.dto.UserDTO;
import matheus.github.calculate.exception.exceptions.user.InvalidUserException;
import matheus.github.calculate.exception.exceptions.user.UsernameAlreadyExistsException;
import matheus.github.calculate.models.uservalidationstrategy.UserValidationStrategy;
import matheus.github.calculate.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsernameUserValidationStrategy implements UserValidationStrategy {

	@Autowired
	private UserRepository userRepository;

	@Override
	public void execute(UserDTO userDTO) {
		String username = userDTO.getUsername();

		if (username.length() <= 3) {
			throw new InvalidUserException("The user must have at least 4 characters");
		}

		if (username.split(" ").length > 1) {
			throw new InvalidUserException("The username must not contain spaces");
		}

		if (userRepository.existsByUsername(userDTO.getUsername())) {
			throw new UsernameAlreadyExistsException(String.format("An user with username %s already exists", userDTO.getUsername()));
		}

	}
}

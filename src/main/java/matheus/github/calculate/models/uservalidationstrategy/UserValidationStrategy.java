package matheus.github.calculate.models.uservalidationstrategy;

import matheus.github.calculate.dto.UserDTO;

@FunctionalInterface
public interface UserValidationStrategy {
	void execute(UserDTO userDTO);
}

package matheus.github.calculate.models.uservalidationstrategy;

import matheus.github.calculate.dto.UserDTO;
import matheus.github.calculate.exception.exceptions.user.EmailAlreadyExistsException;
import matheus.github.calculate.exception.exceptions.user.InvalidUserException;
import matheus.github.calculate.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class EmailUserValidationStrategy implements UserValidationStrategy {

	@Autowired
	private UserRepository userRepository;

	@Override
	public void execute(UserDTO userDTO) {
		String email = userDTO.getEmail();

		String regex = "\\b\\S+@\\w+\\.\\w{2,6}(\\.\\w{2})?\\b";
		Pattern pattern = Pattern.compile(regex);

		if (!pattern.matcher(email).find()) {
			throw new InvalidUserException(String.format("Invalid email address: %s", email));
		}

		if (userRepository.existsByEmail(userDTO.getEmail())) {
			throw new EmailAlreadyExistsException(String.format("An user with e-mail %s already exists", email));
		}

		System.out.println(String.format("E-mail: %s validated with success!", userDTO.getEmail()));
	}
}

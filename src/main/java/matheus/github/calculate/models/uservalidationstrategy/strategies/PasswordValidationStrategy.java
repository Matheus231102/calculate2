package matheus.github.calculate.models.uservalidationstrategy.strategies;

import matheus.github.calculate.dto.UserDTO;
import matheus.github.calculate.exception.exceptions.user.InvalidUserException;
import matheus.github.calculate.models.uservalidationstrategy.UserValidationStrategy;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class PasswordValidationStrategy implements UserValidationStrategy  {

	@Override
	public void execute(UserDTO userDTO) {
		String password = userDTO.getPassword();
		String regex = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z$*&@#]{8,}";
		Pattern pattern = Pattern.compile(regex);
		if (!pattern.matcher(password).find()) {
			throw new InvalidUserException("The password must comply with the rules");
		}
		System.out.println("Password validated with success");
	}
}

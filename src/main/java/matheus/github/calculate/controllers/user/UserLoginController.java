package matheus.github.calculate.controllers.user;

import jakarta.validation.Valid;
import matheus.github.calculate.dto.AuthDTO;
import matheus.github.calculate.exception.exceptions.user.UserNotFoundException;
import matheus.github.calculate.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static matheus.github.calculate.paths.PathConstants.DEFAULT_USER_PATH;
import static matheus.github.calculate.paths.PathConstants.LOGIN_PATH;

@RestController
@RequestMapping(DEFAULT_USER_PATH)
public class UserLoginController {

	@Autowired
	private UserService userService;

	@PostMapping(LOGIN_PATH)
	public ResponseEntity<String> loginUser(@RequestBody @Valid AuthDTO authDTO) throws UserNotFoundException {
		String token = userService.loginUser(authDTO);

		return ResponseEntity
				.status(HttpStatus.OK)
				.header("Authorization", token)
				.body("User: " + authDTO.getLogin() + " successfully logged in");
	}

}

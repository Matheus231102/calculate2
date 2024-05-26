package matheus.github.calculate.controller;

import jakarta.validation.Valid;
import matheus.github.calculate.controller.paths.PathConstants;
import matheus.github.calculate.dto.AuthDTO;
import matheus.github.calculate.dto.UserDTO;
import matheus.github.calculate.exception.exceptions.UserNotFoundException;
import matheus.github.calculate.jwt.JwtService;
import matheus.github.calculate.models.User;
import matheus.github.calculate.security.AuthenticationContext;
import matheus.github.calculate.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(PathConstants.DEFAULT_USER_PATH)
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationContext authenticationContext;

	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) throws UserNotFoundException {
		User user = userService.findById(id);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(user);
	}

	@GetMapping
	public ResponseEntity<User> getUserByToken() throws UserNotFoundException {
		String authenticatedUsername = authenticationContext.getAuthenticatedUsername();
		User user = userService.findByUsername(authenticatedUsername);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(user);
	}

	@PostMapping(PathConstants.REGISTER_PATH)
	public ResponseEntity<User> registerUser(@RequestBody @Valid UserDTO userDTO) {
		User user = userService.registerUser(userDTO);
		String token = jwtService.generateToken(user);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.header("Authorization", token)
				.body(user);
	}

	@PostMapping(PathConstants.LOGIN_PATH)
	public ResponseEntity<String> loginUser(@RequestBody @Valid AuthDTO authDTO) throws UserNotFoundException {
		String token = userService.loginUser(authDTO);

		//todo verificar se é pelo email ou username
		return ResponseEntity
				.status(HttpStatus.OK)
				.header("Authorization", token)
				.body("User: " + authDTO.getLogin() + " successfully logged in");
	}

	@DeleteMapping("/all")
	public ResponseEntity<String> deleteAllUsers() {
		userService.deleteAllUsers();
		return ResponseEntity
				.status(HttpStatus.NO_CONTENT)
				.body("all users deleted successfully");
	}

}

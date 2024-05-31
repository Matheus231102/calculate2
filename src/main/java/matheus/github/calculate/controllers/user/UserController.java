package matheus.github.calculate.controllers.user;

import matheus.github.calculate.exception.exceptions.user.UserNotFoundException;
import matheus.github.calculate.models.User;
import matheus.github.calculate.security.AuthenticationContext;
import matheus.github.calculate.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static matheus.github.calculate.controllers.paths.PathConstants.DEFAULT_USER_PATH;

@RestController
@RequestMapping(DEFAULT_USER_PATH)
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationContext authenticationContext;

	@GetMapping
	public ResponseEntity<User> getUserByToken() throws UserNotFoundException {
		String authenticatedUsername = authenticationContext.getAuthenticatedUsername();
		User user = userService.findByUsername(authenticatedUsername);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(user);
	}

	@GetMapping("/all")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> userList = userService.findAll();
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(userList);
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) throws UserNotFoundException {
		User user = userService.findById(id);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(user);
	}

	@DeleteMapping("/all")
	public ResponseEntity<String> deleteAllUsers() {
		userService.deleteAll();
		return ResponseEntity
				.status(HttpStatus.NO_CONTENT)
				.body("all users deleted successfully");
	}

}

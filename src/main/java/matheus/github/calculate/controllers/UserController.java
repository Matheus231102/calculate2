package matheus.github.calculate.controllers;

import matheus.github.calculate.exception.exceptions.UserNotFoundException;
import matheus.github.calculate.models.User;
import matheus.github.calculate.security.AuthenticationContext;
import matheus.github.calculate.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping
	public ResponseEntity<User> getUserByToken() throws UserNotFoundException {
		String authenticatedUsername = authenticationContext.getAuthenticatedUsername();
		User user = userService.findByUsername(authenticatedUsername);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(user);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/all")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> userList = userService.findAll();
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(userList);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) throws UserNotFoundException {
		User user = userService.findById(id);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(user);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/all")
	public ResponseEntity<String> deleteAllUsers() {
		userService.deleteAll();
		return ResponseEntity
				.status(HttpStatus.NO_CONTENT)
				.body("all users deleted successfully");
	}

}

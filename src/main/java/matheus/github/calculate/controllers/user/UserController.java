package matheus.github.calculate.controllers.user;

import matheus.github.calculate.exception.exceptions.user.UserNotFoundException;
import matheus.github.calculate.models.User;
import matheus.github.calculate.security.AuthenticationContext;
import matheus.github.calculate.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static matheus.github.calculate.paths.PathConstants.DEFAULT_USER_PATH;

@RestController
@RequestMapping(DEFAULT_USER_PATH)
@PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MANAGER')")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationContext authenticationContext;

	@GetMapping
	public ResponseEntity<User> getUser() throws UserNotFoundException {
		String authenticatedUsername = authenticationContext.getAuthenticatedUsername();
		User user = userService.getUser(authenticatedUsername);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(user);
	}

}

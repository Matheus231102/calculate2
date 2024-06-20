package matheus.github.calculate.controllers.user;

import matheus.github.calculate.exception.exceptions.user.UserNotFoundException;
import matheus.github.calculate.models.User;
import matheus.github.calculate.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static matheus.github.calculate.paths.PathConstants.ADMIN_PATH;
import static matheus.github.calculate.paths.PathConstants.DEFAULT_USER_PATH;


@RestController
@RequestMapping(DEFAULT_USER_PATH + ADMIN_PATH)
@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
public class UserAdminController {

	@Autowired
	private UserService userService;

	@GetMapping("/all")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> userList = userService.getAllUser();
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(userList);
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getUser(@PathVariable Long id) throws UserNotFoundException {
		User user = userService.getUser(id);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(user);
	}

	@DeleteMapping("/all")
	public ResponseEntity<String> deleteAllUsers() {
		userService.deleteAllUser();
		return ResponseEntity
				.status(HttpStatus.NO_CONTENT)
				.body("all users deleted successfully");
	}

}

package matheus.github.calculate.controllers.user;

import jakarta.validation.Valid;
import matheus.github.calculate.dto.EmailDTO;
import matheus.github.calculate.dto.UserDTO;
import matheus.github.calculate.dto.UsernameDTO;
import matheus.github.calculate.jwt.JwtService;
import matheus.github.calculate.models.User;
import matheus.github.calculate.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static matheus.github.calculate.paths.PathConstants.DEFAULT_USER_PATH;
import static matheus.github.calculate.paths.PathConstants.REGISTER_PATH;

@RestController
@RequestMapping(DEFAULT_USER_PATH)
public class UserRegisterController {

	@Autowired
	private UserService userService;

	@Autowired
	private JwtService jwtService;

	@PostMapping(REGISTER_PATH)
	public ResponseEntity<User> addUser(@RequestBody @Valid UserDTO userDTO) throws Exception {
		User user = userService.addUser(userDTO);
		String token = jwtService.generateToken(user);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.header("Authorization", token)
				.body(user);
	}

	@GetMapping(REGISTER_PATH + "/boolean")
	public ResponseEntity<Boolean> getBoolean() {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(true);
	}

	@PostMapping(REGISTER_PATH + "/emailAvailable")
	public ResponseEntity<Boolean> isEmailAvailable(@RequestBody @Valid EmailDTO email) throws Exception {
		boolean isEmailAvailable = userService.isEmailAvailable(email.getEmail());
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(isEmailAvailable);
	}

	@PostMapping(REGISTER_PATH + "/usernameAvailable")
	public ResponseEntity<Boolean> isUsernameAvailable(@RequestBody @Valid UsernameDTO username) throws Exception {
		boolean isUsernameAvailable = userService.isUsernameAvailable(username.getUsername());
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(isUsernameAvailable);
	}

}

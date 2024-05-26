package matheus.github.calculate.controllers;

import jakarta.validation.Valid;
import matheus.github.calculate.controllers.paths.PathConstants;
import matheus.github.calculate.dto.UserDTO;
import matheus.github.calculate.jwt.JwtService;
import matheus.github.calculate.models.User;
import matheus.github.calculate.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static matheus.github.calculate.controllers.paths.PathConstants.*;

@RestController
@RequestMapping(DEFAULT_USER_PATH)
public class UserRegisterController {

	@Autowired
	private UserService userService;

	@Autowired
	private JwtService jwtService;

	@PostMapping(REGISTER_PATH)
	public ResponseEntity<User> registerUser(@RequestBody @Valid UserDTO userDTO) {
		User user = userService.registerUser(userDTO);
		String token = jwtService.generateToken(user);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.header("Authorization", token)
				.body(user);
	}

}

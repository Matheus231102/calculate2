package matheus.github.calculate.controller;

import matheus.github.calculate.dto.UserDTO;
import matheus.github.calculate.exception.exceptions.UserNotFoundException;
import matheus.github.calculate.models.User;
import matheus.github.calculate.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/{id}")
	public User getUserById(@PathVariable Long id) throws UserNotFoundException {
		return userService.findById(id);
	}

	@PostMapping
	public User insertUser(@RequestBody UserDTO userDTO) {
		return userService.insertUser(userDTO);
	}
}

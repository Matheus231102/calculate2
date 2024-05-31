package matheus.github.calculate.services;

import matheus.github.calculate.dto.AuthDTO;
import matheus.github.calculate.dto.UserDTO;
import matheus.github.calculate.exception.exceptions.user.UserNotFoundException;
import matheus.github.calculate.jwt.JwtService;
import matheus.github.calculate.mapper.user.UserMapper;
import matheus.github.calculate.models.User;
import matheus.github.calculate.models.uservalidationstrategy.UserValidationStrategy;
import matheus.github.calculate.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private List<UserValidationStrategy> userValidationStrategies;

	public User findById(Long id) throws UserNotFoundException {
		Optional<User> optionalUser = userRepository.findById(id);
		if (optionalUser.isEmpty()) {
			throw new UserNotFoundException("User not found by provided id: " + id);
		}
		return optionalUser.get();
	}

	public User findByUsername(String username) throws UserNotFoundException {
		Optional<User> optionalUser = userRepository.findByUsername(username);
		if (optionalUser.isEmpty()) {
			throw new UserNotFoundException(String.format("User not found by provided username: %s", username));
		}
		return optionalUser.get();
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public User register(UserDTO userDTO) {
		userValidationStrategies.forEach(userValidationStrategy -> userValidationStrategy.execute(userDTO));
		User user = userMapper.toEntity(userDTO);

		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);

		return userRepository.save(user);
	}

	public String login(AuthDTO authDTO) throws UserNotFoundException {
		//todo verificar se consigo utilizar algum padrão aqui
		if (!userRepository.existsByUsername(authDTO.getLogin())) {
			throw new UserNotFoundException(String.format("User not found by provided username: %s", authDTO.getLogin()));
		}

		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authDTO.getLogin(), authDTO.getPassword())
		);

		return jwtService.getToken(authDTO.getLogin());
	}

	public void deleteAll() {
		userRepository.deleteAll();
	}

}

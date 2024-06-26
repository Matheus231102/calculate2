package matheus.github.calculate.services;

import matheus.github.calculate.dto.AuthDTO;
import matheus.github.calculate.dto.UserDTO;
import matheus.github.calculate.exception.exceptions.UserNotFoundException;
import matheus.github.calculate.jwt.JwtService;
import matheus.github.calculate.mapper.user.UserMapper;
import matheus.github.calculate.models.User;
import matheus.github.calculate.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

	public User findById(Long id) throws UserNotFoundException {
		//todo verificar validações necessárias para o método
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			return user.get();
		}
		throw new UserNotFoundException("User not found by provided id: " + id);
	}

	public User findByUsername(String username) throws UserNotFoundException {
		Optional<User> optionalUser = userRepository.findByUsername(username);
		if (optionalUser.isPresent()) {
			return optionalUser.get();
		}
		throw new UserNotFoundException("User not found by provided username: " + username);
	}

	public User registerUser(UserDTO userDTO) {
		User user = userMapper.toEntity(userDTO);
		encodeUserPassword(user);
		return userRepository.save(user);
	}

	public String loginUser(AuthDTO authDTO) throws UserNotFoundException {
		if (!userRepository.existsByUsername(authDTO.getLogin())) {
			throw new UserNotFoundException(String.format("User not found by provided username: %s", authDTO.getLogin()));
		}

		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authDTO.getLogin(), authDTO.getPassword())
		);

		return jwtService.getToken(authDTO.getLogin());
	}

	public void deleteAllUsers() {
		userRepository.deleteAll();
	}

	private void encodeUserPassword(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
	}

}

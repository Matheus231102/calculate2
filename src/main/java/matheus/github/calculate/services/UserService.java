package matheus.github.calculate.services;

import matheus.github.calculate.dto.UserDTO;
import matheus.github.calculate.exception.exceptions.UserNotFoundException;
import matheus.github.calculate.mapper.user.UserMapper;
import matheus.github.calculate.models.User;
import matheus.github.calculate.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserMapper userMapper;

//	public User findByEmail(String email) {
//
//	}

	public User findById(Long id) throws UserNotFoundException {
		//todo verificar validações necessárias para o método
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			return user.get();
		}
		throw new UserNotFoundException("User not found by provided id");
	}

	public User findByUsername(String username) throws UserNotFoundException {
		Optional<User> optionalUser = userRepository.findByUsername(username);
		if (optionalUser.isPresent()) {
			return optionalUser.get();
		}
		throw new UserNotFoundException("User not found by provided username");
	}


	public User insertUser(UserDTO userDTO) {
		User user = userMapper.toEntity(userDTO);
		return userRepository.save(user);
	}
}

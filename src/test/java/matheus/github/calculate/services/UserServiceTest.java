package matheus.github.calculate.services;


import matheus.github.calculate.dto.UserDTO;
import matheus.github.calculate.exception.exceptions.user.UserNotFoundException;
import matheus.github.calculate.jwt.JwtService;
import matheus.github.calculate.mapper.user.UserMapper;
import matheus.github.calculate.models.User;
import matheus.github.calculate.models.uservalidationstrategy.UserValidationStrategy;
import matheus.github.calculate.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static matheus.github.calculate.commons.UserCommons.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private UserMapper userMapper;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private JwtService jwtService;

	@Mock
	private AuthenticationManager authenticationManager;

	@Mock
	private List<UserValidationStrategy> userValidationStrategies;

	private User user;
	private UserDTO userDTO;
	private User userWithId;

	@BeforeEach
	void setUp() {

		userDTO = UserDTO.builder()
				.name("teste")
				.username("testeDTO")
				.email("teste@gmail.com")
				.password("Password123")
				.build();

		user = User.builder()
				.name(userDTO.getName())
				.username(userDTO.getUsername())
				.email(userDTO.getEmail())
				.password(userDTO.getPassword())
				.build();

		userWithId = user;
		userWithId.setId(1L);
	}

	@Test
	void get_User_returnsUser() throws UserNotFoundException {
		when(userRepository.findById(VALID_ID)).thenReturn(Optional.of(VALID_USER));

		User sut = userService.getUser(VALID_ID);

		assertThat(sut).isEqualTo(VALID_USER);
		assertThat(sut.getUsername()).isEqualTo(VALID_USER.getUsername());
		assertThat(sut.getEmail()).isEqualTo(VALID_USER.getEmail());
	}

	@Test
	void get_User_ThrowException() throws UserNotFoundException {
		when(userRepository.findById(INVALID_ID)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> userService.getUser(INVALID_ID)).isInstanceOf(UserNotFoundException.class);
	}

	@Test
	void get_User_ReturnsUser() throws UserNotFoundException {
		when(userRepository.findByUsername(VALID_USER.getUsername())).thenReturn(Optional.of(VALID_USER));

		User sut = userService.getUser(VALID_USER.getUsername());

		assertThat(sut).isEqualTo(VALID_USER);
		assertThat(sut.getUsername()).isEqualTo(VALID_USER.getUsername());
	}

	@Test
	void get_User_ThrowsException() throws UserNotFoundException {
		when(userRepository.findByUsername("Unexisting username")).thenReturn(Optional.empty());

		assertThatThrownBy(() -> userService.getUser("Unexisting username")).isInstanceOf(UserNotFoundException.class);
	}

	@Test
	void getUserAll_ReturnsUsers_DoesNotThrowException() {
		List<User> userList = List.of(VALID_USER, VALID_USER2);

		when(userRepository.findAll()).thenReturn(userList);
		List<User> list = userService.getAllUser();

		assertThat(list).isEqualTo(userList);
		assertThat(list.size()).isEqualTo(2);
	}


}
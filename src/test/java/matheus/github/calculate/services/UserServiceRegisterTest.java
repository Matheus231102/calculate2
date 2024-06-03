package matheus.github.calculate.services;

import matheus.github.calculate.dto.UserDTO;
import matheus.github.calculate.exception.exceptions.user.InvalidUserException;
import matheus.github.calculate.exception.exceptions.user.UsernameAlreadyExistsException;
import matheus.github.calculate.jwt.JwtService;
import matheus.github.calculate.mapper.user.UserMapper;
import matheus.github.calculate.models.User;
import matheus.github.calculate.models.uservalidationstrategy.UserValidationStrategy;
import matheus.github.calculate.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceRegisterTest {

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private UserMapper userMapper;

	@MockBean
	private PasswordEncoder passwordEncoder;

	@MockBean
	private JwtService jwtService;

	@Autowired
	private List<UserValidationStrategy> userValidationStrategies;

	@Autowired
	private UserService userService;

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
	void registerUser_WithCorrectDTO_ReturnsUser() throws Exception {
		when(userMapper.toEntity(userDTO)).thenReturn(user);
		when(userRepository.save(user)).thenReturn(userWithId);

		User sut = userService.register(userDTO);
		assertThat(sut.getId()).isNotNull();
		assertThat(sut.getId()).isNotNegative();
		assertThat(sut.getId()).isNotZero();
		assertThat(sut.getId()).isEqualTo(1);

		assertThat(sut.getUsername()).isEqualTo(userDTO.getUsername());
		assertThat(sut.getName()).isEqualTo(userDTO.getName());

		assertThat(sut.getUsername().length()).isGreaterThan(3);
	}

	@Test
	void registerUser_WithUsernameLowerThan4_ThrowsException() throws Exception {
		userDTO.setUsername("INV");
		when(userMapper.toEntity(userDTO)).thenReturn(user);

		assertThatThrownBy(() -> userService.register(userDTO)).isInstanceOf(InvalidUserException.class);
	}

	@Test
	void registerUser_UsernameContainsSpace_ThrowsException() {
		userDTO.setUsername("INV TESTE");
		when(userMapper.toEntity(userDTO)).thenReturn(user);

		assertThatThrownBy(() -> userService.register(userDTO)).isInstanceOf(InvalidUserException.class);
	}

	@Test
	void registerUser_WithExistingUsername_ThrowsException() {
		userDTO.setUsername("ExistingUsername");

		when(userMapper.toEntity(userDTO)).thenReturn(user);
		when(userRepository.existsByUsername("ExistingUsername")).thenReturn(true);

		assertThatThrownBy(() -> userService.register(userDTO)).isInstanceOf(UsernameAlreadyExistsException.class);
	}

	@Test
	void registerUser_WithInvalidEmail_ThrowsException() {
		userDTO.setEmail("invalid-email");
		when(userMapper.toEntity(userDTO)).thenReturn(user);

		assertThatThrownBy(() -> userService.register(userDTO)).isInstanceOf(InvalidUserException.class);
	}

}

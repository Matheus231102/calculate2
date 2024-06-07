package matheus.github.calculate.repositories;

import matheus.github.calculate.enums.Role;
import matheus.github.calculate.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest(properties = {
		"spring.jpa.hibernate.ddl-auto=create-drop",
		"spring.datasource.url=jdbc:mysql://localhost:3306/calculate_macro_db_test"
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TestEntityManager testEntityManager;

	private User correctUser;
	private User correctUser1;
	private User correctUser2;

	@BeforeEach
	void setUp() {
		correctUser = new User(null, "matheus12332", "Matheus Badia", "matheus@gmail.com", "Acdc231102",
				Role.USER, new ArrayList<>(), new ArrayList<>());

		correctUser1 = new User(null, "matheus1233123421", "Matheus de Souza Badia", "matheus123@gmail.com", "Acdc231102",
				Role.USER, new ArrayList<>(), new ArrayList<>());

		correctUser2 = new User(null, "matheus12332123151", "Matheus BA", "matheus23@gmail.com", "Acdc231102",
				Role.USER, new ArrayList<>(), new ArrayList<>());

		List<User> userList = new ArrayList<>();

		userList.add(correctUser);
		userList.add(correctUser1);
		userList.add(correctUser2);

		userRepository.saveAll(userList);
	}

	@Test
	void createUser_WithInvalidData_ReturnsUser() {
		User user = new User();
		User user1 = new User(1L, "", "", "", "", Role.USER, new ArrayList<>(), new ArrayList<>());

		assertThatThrownBy(() -> userRepository.save(user));
		assertThatThrownBy(() -> userRepository.save(user1));
	}

	@Test
	void createUser_WithValidData_ReturnsUser() {
		User user = userRepository.save(correctUser);

		assertThat(user).isEqualTo(testEntityManager.find(User.class, 1L));
	}

	@Test
	void findByUsername_WithValidUsername_ReturnsUser() {
		Optional<User> optionalUser = userRepository.findByUsername(correctUser.getUsername());

		assertThat(optionalUser.get()).isNotNull();
		assertThat(optionalUser.get()).isEqualTo(correctUser);
		assertThat(optionalUser.get().getUsername()).isEqualTo(correctUser.getUsername());
	}

	@Test
	void findByUsername_WithInvalidUsername_ThrowsException() {
		Optional<User> optionalUser = userRepository.findByUsername("Incorrect Username");

		assertThat(optionalUser).isEqualTo(Optional.empty());
	}

	@Test
	void existsByEmail_WithValidEmail_ReturnsTrue() {
		boolean b = userRepository.existsByEmail(correctUser.getEmail());

		assertThat(b).isTrue();
	}

	@Test
	void existsByEmail_WithInvalidEmail_ReturnsTrue() {
		boolean b = userRepository.existsByEmail("Incorrect Email");

		assertThat(b).isFalse();
	}

	@Test
	void existsByUsername_WithValidUsername_ReturnsTrue() {
		boolean b = userRepository.existsByUsername(correctUser.getUsername());

		assertThat(b).isTrue();
	}

	@Test
	void existsByUsername_WithInvalidUsername_ReturnsTrue() {
		boolean b = userRepository.existsByEmail("Incorrect Username");

		assertThat(b).isFalse();
	}

}


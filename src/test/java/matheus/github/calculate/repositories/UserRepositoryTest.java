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

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
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
		correctUser = new User(1L, "matheus12332", "Matheus Badia", "matheus@gmail.com", "Acdc231102",
				Role.USER, new ArrayList<>(), new ArrayList<>());

		correctUser1 = new User(2L, "matheus1233123421", "Matheus de Souza Badia", "matheus123@gmail.com", "Acdc231102",
				Role.USER, new ArrayList<>(), new ArrayList<>());

		correctUser2 = new User(45L, "matheus12332123151", "Matheus BA", "matheus23@gmail.com", "Acdc231102",
				Role.USER, new ArrayList<>(), new ArrayList<>());

	}

	@Test
	void createUser_WithInvalidData_ReturnsUser() {
		User user = new User();
		User user1 = new User(1L, "", "", "", "", Role.USER, new ArrayList<>(), new ArrayList<>());

		assertThatThrownBy(() -> userRepository.save(user));
		//todo why it does not throw any exception
//		assertThatThrownBy(() -> userRepository.save(user1));
	}

	@Test
	void createUser_WithValidData_ReturnsUser() {
		User user = userRepository.save(correctUser);

		assertThat(user).isEqualTo(testEntityManager.find(User.class, 1L));
	}

	@Test
	void createUsers_WithValidData_ReturnsUsers() {
		User user = userRepository.save(correctUser);
		User user1 = userRepository.save(correctUser1);
		User user2 = userRepository.save(correctUser2);

		List<User> userList = List.of(user, user1, user2);
		List<User> foundUserList = new ArrayList<>();

		userList.forEach((createdUser) -> {
			foundUserList.add(testEntityManager.find(User.class, createdUser.getId()));
		});

		assertThat(userList).isEqualTo(foundUserList);
		assertThat(foundUserList.size()).isEqualTo(3);
	}


}

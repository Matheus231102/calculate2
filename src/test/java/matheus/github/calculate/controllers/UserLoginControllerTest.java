package matheus.github.calculate.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import matheus.github.calculate.dto.UserDTO;
import matheus.github.calculate.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.*;
import static matheus.github.calculate.controllers.paths.PathConstants.*;

import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.properties")
class UserLoginControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private UserService userService;

	@Qualifier("objectMapper")
	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
	}

	@Test
	void testingDatabaseConnection() {


	}

	@Test
//	@Disabled
	void givenCorrectUsernameAndPassword_whenLoginUser_thenReturnToken() throws JsonProcessingException {
		UserDTO userDTO = UserDTO.builder()
				.username("teste")
				.name("teste cena")
				.password("teste")
				.email("teste@cena")
				.build();

		userService.register(userDTO);

		String userDtoJson = objectMapper.writeValueAsString(userDTO);

		given()
			.contentType(ContentType.JSON)
			.body(userDtoJson)
		.when()
			.post(DEFAULT_USER_PATH + REGISTER_PATH);

		given()
			.contentType(ContentType.JSON)
		.when()
			.post(DEFAULT_USER_PATH + "/all")
		.then()
			.statusCode(HttpStatus.OK.value());

	}
}
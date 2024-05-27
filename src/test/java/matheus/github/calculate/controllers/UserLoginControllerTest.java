package matheus.github.calculate.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import matheus.github.calculate.controllers.paths.PathConstants;
import matheus.github.calculate.dto.UserDTO;
import matheus.github.calculate.enums.EnumRole;
import matheus.github.calculate.models.User;
import matheus.github.calculate.services.UserService;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static matheus.github.calculate.controllers.paths.PathConstants.*;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
		userService.deleteAll();
		RestAssured.port = port;
	}

	@Test
	void givenCorrectUsernameAndPassword_whenLoginUser_thenReturnToken() throws JsonProcessingException {
		UserDTO userDTO = UserDTO.builder()
				.username("john")
				.name("john cena")
				.password("john123")
				.email("john@cena")
				.build();

		User user = User.builder()
				.id(1L)
				.username("john")
				.name("john cena")
				.email("john@cena")
				.build();

		userService.registerUser(userDTO);

		String userDtoJson = objectMapper.writeValueAsString(userDTO);
		String userJson = objectMapper.writeValueAsString(user);

		given()
			.contentType(ContentType.JSON)
			.body(userDtoJson)
			.port(port)
		.when()
			.post(DEFAULT_USER_PATH + REGISTER_PATH)
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
}
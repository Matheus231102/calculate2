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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static io.restassured.RestAssured.given;
import static matheus.github.calculate.controllers.paths.PathConstants.DEFAULT_USER_PATH;
import static matheus.github.calculate.controllers.paths.PathConstants.REGISTER_PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
class UserLoginControllerIntegrationTest {

	@Value("${used.profile}")
	private String usedProfile;

	@Autowired
	private UserService userService;

	@Autowired
	@Qualifier("objectMapper")
	private ObjectMapper objectMapper;

	private UserDTO userDTO;

	@BeforeEach
	void setUp() {
		userDTO = UserDTO.builder()
				.username("matheusteste")
				.name("teste cena")
				.password("teste")
				.email("teste@cena")
				.build();
	}

	@Test
	void assertCorrectDatabaseConnection() {
		assertEquals("teste", usedProfile);
//		assertEquals(9090, serverPort);
	}

	@Test
	void givenCorrectUsernameAndPassword_whenLoginUser_thenReturnToken() throws JsonProcessingException {



//		String userDtoJson = objectMapper.writeValueAsString(userDTO);
//
//		given()
//			.contentType(ContentType.JSON)
//			.body(userDtoJson)
//		.when()
//			.post(DEFAULT_USER_PATH + REGISTER_PATH);
//		given()
//			.contentType(ContentType.JSON)
//		.when()
//			.post(DEFAULT_USER_PATH + "/register")
//		.then()
//			.statusCode(HttpStatus.CREATED.value());

	}
}
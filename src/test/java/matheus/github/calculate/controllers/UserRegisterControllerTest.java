package matheus.github.calculate.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import matheus.github.calculate.dto.UserDTO;
import matheus.github.calculate.jwt.JwtService;
import matheus.github.calculate.models.User;
import matheus.github.calculate.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserRegisterControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@MockBean
	private JwtService jwtService;

	@Autowired
	private ObjectMapper objectMapper;

	public UserDTO userDTO;
	public User user;

	@BeforeEach
	void setUp() {
		userDTO = UserDTO.builder()
				.name("name")
				.username("username")
				.email("email@email.com")
				.password("Password123")
				.build();

		user = User.builder()
				.id(1L)
				.name(userDTO.getName())
				.username(userDTO.getUsername())
				.email(userDTO.getEmail())
				.password(userDTO.getPassword())
				.build();

	}

	@Test
	@WithAnonymousUser
	void addUserUser_WithCorrectData_ReturnsAndJwt() throws Exception {
		String token = "token";

		when(userService.addUser(eq(userDTO))).thenReturn(user);
		when(jwtService.generateToken(eq(user))).thenReturn(token);

		ResultActions perform = mockMvc.perform(post("/users/register")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(userDTO)));

		ResultActions resultActions = perform
				.andExpect(status().isCreated())
				.andExpect(header().string("Authorization", token))
				.andExpect(jsonPath("$.id").value(user.getId()))
				.andExpect(jsonPath("$.name").value(user.getName()));

		String bodyAsString = resultActions.andReturn().getResponse().getContentAsString();
		System.out.println(bodyAsString);
	}

}

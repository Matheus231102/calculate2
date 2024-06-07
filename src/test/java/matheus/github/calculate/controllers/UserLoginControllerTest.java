package matheus.github.calculate.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import matheus.github.calculate.dto.AuthDTO;
import matheus.github.calculate.exception.exceptions.user.UserNotFoundException;
import matheus.github.calculate.models.User;
import matheus.github.calculate.services.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserLoginControllerTest {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@BeforeEach
	void setUp() {

	}

	@Test
	void loginUser_WithUnexistingUsername_ReturnsException() throws Exception {
		AuthDTO authDTO = new AuthDTO("Unexisting Login", "default password");

		when(userService.login(eq(authDTO))).thenThrow(new UserNotFoundException(String.format("User not found by provided username: %s ", authDTO.getLogin())));

		ResultActions perform = mockMvc.perform(post("/users/login")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(authDTO)));

		ResultActions resultActions = perform
			.andExpect(jsonPath("$.error").value("User not found"))
			.andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()));

		String bodyAsString = resultActions.andReturn().getResponse().getContentAsString();

		Assertions.assertThat(bodyAsString).isNotEmpty();
		Assertions.assertThat(bodyAsString).contains(authDTO.getLogin());
	}

	@Test
	void loginUser_WithAuthData_ReturnJwtInHeader() throws Exception {
		AuthDTO authDTO = new AuthDTO("Unexisting Login", "default password");

		String token = "token";
		when(userService.login(eq(authDTO))).thenReturn(token);

		ResultActions perform = mockMvc.perform(post("/users/login")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(authDTO)));

		ResultActions resultActions = perform
			.andExpect(header().string("Authorization", token))
			.andExpect(status().isOk());
	}

}

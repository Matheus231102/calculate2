package matheus.github.calculate.security;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class UnprotectedEndpoints {

	public static final String LOGIN_PATH = "/users/login";
	public static final String REGISTER_PATH = "/users/register";
	public static final String EMAIL_AVAILABLE_PATH = "/users/register/emailAvailable";
	public static final String USERNAME_AVAILABLE_PATH = "/users/register/usernameAvailable";
	public static final String BOOLEAN_PATH = "/users/register/boolean";

	public List<String> getUnprotectedEndpoints() {
		return Arrays.asList(
				LOGIN_PATH,
				REGISTER_PATH,
				EMAIL_AVAILABLE_PATH,
				USERNAME_AVAILABLE_PATH,
				BOOLEAN_PATH
		);
	}

	public String[] getUnprotectedEndpointsVector() {
		String[] endpoints = new String[getUnprotectedEndpoints().size()];
		return getUnprotectedEndpoints().toArray(endpoints);
	}

}

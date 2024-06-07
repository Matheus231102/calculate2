package matheus.github.calculate.security;

import matheus.github.calculate.paths.PathConstants;
import matheus.github.calculate.security.filters.ValidateJwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

import static matheus.github.calculate.enums.Role.*;
import static matheus.github.calculate.paths.PathConstants.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private ValidateJwtFilter validateJwtFilter;

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(AbstractHttpConfigurer::disable)
				.cors(AbstractHttpConfigurer::disable)
				.sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
				.authorizeHttpRequests(
						requests -> requests
								.requestMatchers(DEFAULT_USER_PATH + LOGIN_PATH).permitAll()
								.requestMatchers(DEFAULT_USER_PATH + REGISTER_PATH).permitAll()
								.requestMatchers(DEFAULT_USER_PATH + ADMIN_PATH + "/**").hasAnyRole(MANAGER.name(), ADMIN.name())
								.requestMatchers(DEFAULT_MEAL_PATH).hasAnyRole(MANAGER.name(), ADMIN.name(), USER.name())
								.anyRequest().authenticated()
				)
				.addFilterBefore(validateJwtFilter, AuthorizationFilter.class)
				.httpBasic(AbstractHttpConfigurer::disable)
				.formLogin(AbstractHttpConfigurer::disable)
				.build();
	}
	//implementar sistema de hierarquias no spring boot
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}


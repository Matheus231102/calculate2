package matheus.github.calculate.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthDTO {
	@NotNull
	@NotEmpty
	private String login;

	@NotNull
	@NotEmpty
	private String password;
}

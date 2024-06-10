package matheus.github.calculate.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserDTO {

	@NotEmpty(message = "The username must not be empty or null")
	@EqualsAndHashCode.Include
	private String username;

	@NotEmpty(message = "The name must not be empty or null")
	private String name;

	@NotEmpty(message = "The e-mail must not be empty or null")
	@EqualsAndHashCode.Include
	private String email;

	@NotEmpty(message = "The password must not be empty or null")
	private String password;
}

package matheus.github.calculate.commons;

import matheus.github.calculate.dto.UserDTO;
import matheus.github.calculate.models.User;

public class UserCommons {
	public static final long VALID_ID = 1;
	private static final long VALID_ID_2 = 2;
	public static final long INVALID_ID = Long.MAX_VALUE;

	public static final UserDTO VALID_USERDTO = UserDTO.builder()
			.name("name")
			.username("username")
			.email("email@email.com")
			.password("Password123")
			.build();

	public static final User VALID_USER = User.builder()
			.id(VALID_ID)
			.name(VALID_USERDTO.getName())
			.username(VALID_USERDTO.getUsername())
			.email(VALID_USERDTO.getEmail())
			.password(VALID_USERDTO.getPassword())
			.build();

	public static final User VALID_USER2 = User.builder()
			.id(VALID_ID_2)
			.name("name2")
			.username("username2")
			.email("email2@email.com")
			.password("password2")
			.build();

}

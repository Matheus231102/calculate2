package matheus.github.calculate.permissions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

	MANAGER_READ("manager:read"),
	MANAGER_CREATE("manager:read"),
	MANAGER_UPDATE("manager:read"),
	MANAGER_DELETE("manager:read"),

	ADMIN_READ("admin:read"),
	ADMIN_CREATE("admin:create"),
	ADMIN_UPDATE("admin:update"),
	ADMIN_DELETE("admin:delete"),

	USER_READ("user:read"),
	USER_CREATE("user:read"),
	USER_UPDATE("user:read"),
	USER_DELETE("user:read");

	@Getter
	private final String permission;
}

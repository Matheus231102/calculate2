package matheus.github.calculate.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import matheus.github.calculate.permissions.Permission;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static matheus.github.calculate.permissions.Permission.*;

@RequiredArgsConstructor
public enum Role {
	USER(Set.of(
			USER_READ,
			USER_CREATE,
			USER_UPDATE,
			USER_DELETE
	)),
	ADMIN(Set.of(
			ADMIN_READ,
			ADMIN_CREATE,
			ADMIN_UPDATE,
			ADMIN_DELETE,
			USER_READ,
			USER_CREATE,
			USER_UPDATE,
			USER_DELETE
	)),
	MANAGER(Set.of(
			ADMIN_READ,
			ADMIN_CREATE,
			ADMIN_UPDATE,
			ADMIN_DELETE,
			USER_READ,
			USER_CREATE,
			USER_UPDATE,
			USER_DELETE
	));

	@Getter
	private final Set<Permission> permissions;

	public List<SimpleGrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = getPermissions().stream()
				.map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
				.collect(Collectors.toList());

		authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

		return authorities;
	}
}

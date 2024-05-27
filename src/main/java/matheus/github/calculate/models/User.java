package matheus.github.calculate.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import matheus.github.calculate.enums.EnumRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "tb_users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	@Column(unique = true, nullable = false)
	@EqualsAndHashCode.Include
	private String username;

	@Column(nullable = false)
	private String name;

	@Column(unique = true, nullable = false)
	@EqualsAndHashCode.Include
	private String email;

	@JsonIgnore
	@Column(nullable = false)
	private String password;

	@JsonIgnore
	@Enumerated(EnumType.STRING)
	private EnumRole role;

	@PrePersist
	private void onCreate() {
		setRole(EnumRole.USER);
	}

	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		switch (this.getRole()) {
			case MANAGER -> {
				return List.of(
						new SimpleGrantedAuthority("ROLE_MANAGER"),
						new SimpleGrantedAuthority("ROLE_ADMIN"),
						new SimpleGrantedAuthority("ROLE_USER")
				);
			}
			case ADMIN -> {
				return List.of(
						new SimpleGrantedAuthority("ROLE_ADMIN"),
						new SimpleGrantedAuthority("ROLE_USER")
				);
			}
			case USER -> {
				return List.of(
						new SimpleGrantedAuthority("ROLE_USER")
				);
			}
			case null, default -> {
				return null;
			}
		}
	}


	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return true;
	}
}

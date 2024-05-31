package matheus.github.calculate.security;

import matheus.github.calculate.exception.exceptions.user.InvalidPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UsernamePasswordAuthenticationProvider implements AuthenticationManager {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
	   String username = authentication.getName();
	   String password = authentication.getCredentials().toString();

	   UserDetails userDetails = userDetailsService.loadUserByUsername(username);

	   if (!passwordEncoder.matches(password, userDetails.getPassword())) {
		   throw new InvalidPasswordException("you must enter valid password");
	   }

	   return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
	}

}

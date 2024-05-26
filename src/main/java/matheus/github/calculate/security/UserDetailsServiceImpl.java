package matheus.github.calculate.security;

import matheus.github.calculate.models.User;
import matheus.github.calculate.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
	private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optionalUser = userRepository.findByUsername(username);
		if (optionalUser.isPresent()) {
		  return (UserDetails) optionalUser.get();
	   }
	   throw new UsernameNotFoundException("User " + username + " not found");
    }
}

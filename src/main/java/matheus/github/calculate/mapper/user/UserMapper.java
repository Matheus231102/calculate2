package matheus.github.calculate.mapper.user;

import matheus.github.calculate.dto.UserDTO;
import matheus.github.calculate.models.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

	@Autowired
	private ModelMapper modelMapper;

	public UserDTO toDTO(User user) {
		return modelMapper.map(user, UserDTO.class);
	}
	//todo passar AuthDTO para DTO

	public User toEntity(UserDTO userDTO) {
		return modelMapper.map(userDTO, User.class);
	}

}

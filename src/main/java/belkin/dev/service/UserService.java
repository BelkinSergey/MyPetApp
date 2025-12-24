package belkin.dev.service;

import belkin.dev.model.UserDto;
import org.springframework.stereotype.Component;


@Component
public interface UserService {

    UserDto createUser(UserDto userToCreate);

    UserDto findUserById(Long id);

    UserDto updateUser(Long id, UserDto user);

    void deleteUser(Long id);


}

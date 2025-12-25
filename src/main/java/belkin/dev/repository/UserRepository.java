package belkin.dev.repository;

import belkin.dev.model.UserDto;

public interface UserRepository {

    UserDto createUser(UserDto userToCreate);

    UserDto findUserById(Long id);

    UserDto updateUser(Long id, UserDto user);

    void deleteUser(Long id);
}




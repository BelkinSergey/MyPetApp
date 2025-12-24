package belkin.dev.repository;

import belkin.dev.model.UserDto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Repository
public class ImplUserRepository implements UserRepository {

    private final Map<Long, UserDto> userMap;
    private Long idCounter;

    public ImplUserRepository() {
        this.userMap = new HashMap<>();
        this.idCounter = 0L;
    }

    public UserDto createUser(UserDto userToCreate) {
        String mail = userToCreate.getEmail();
        var result = userMap.values().stream()
                .filter(userDto -> userDto.getEmail().equals(mail))
                .findFirst();
        if (result.isPresent()) {
            throw new IllegalArgumentException("Пользователь с  email " + mail + " уже существует!");
        }
        var id = ++idCounter;
        var newUser = new UserDto(id,
                userToCreate.getName(),
                userToCreate.getEmail(),
                userToCreate.getAge(),
                new ArrayList<>());

        userMap.put(id, newUser);
        return newUser;
    }

    public UserDto findUserById(Long id) {
        UserDto user = userMap.get(id);
        if (user == null) {
            throw new NoSuchElementException("Пользователь с id " + id + " не найден!");
        }
        return user;
    }

    public UserDto updateUser(Long id, UserDto userToUpdate) {
        if (userMap.get(id) == null) {
            throw new NoSuchElementException("пользователь с id " + id + "не найден!");
        }
        var updateUser = new UserDto(
                id,
                userToUpdate.getName(),
                userToUpdate.getEmail(),
                userToUpdate.getAge(),
                new ArrayList<>()
        );
        userMap.put(id, updateUser);
        return updateUser;
    }

    public void deleteUser(Long id) {
        userMap.remove(id);
    }

}

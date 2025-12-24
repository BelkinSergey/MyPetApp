package belkin.dev.service;

import belkin.dev.model.PetDto;
import belkin.dev.model.UserDto;
import belkin.dev.repository.PetRepository;
import belkin.dev.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ImplUserService implements UserService {

    private final UserRepository userRepository;

    private final PetRepository petRepository;

    public ImplUserService(UserRepository userRepository, PetRepository petRepository) {
        this.userRepository = userRepository;
        this.petRepository = petRepository;
    }


    @Override
    public UserDto createUser(UserDto userToCreate) {
        return userRepository.createUser(userToCreate);
    }

    @Override
    public UserDto findUserById(Long id) {
        return userRepository.findUserById(id);
    }

    @Override
    public UserDto updateUser(Long id, UserDto user) {
        return userRepository.updateUser(id, user);
    }

    @Override
    public void deleteUser(Long id) {
        var user = findUserById(id);
        user.getPets().stream()
                .map(PetDto::getId)
                .forEach(petRepository::deletePetById);

        userRepository.deleteUser(id);


    }
}

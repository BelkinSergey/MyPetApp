package belkin.dev.service;

import belkin.dev.model.PetDto;
import belkin.dev.repository.PetRepository;
import belkin.dev.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ImplPetService implements PetService {

    private final PetRepository petRepository;

    private final UserRepository userRepository;

    public ImplPetService(PetRepository petRepository, UserRepository userRepository) {

        this.petRepository = petRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PetDto createPet(PetDto petToCreate) {
        var user = userRepository.findUserById(petToCreate.getUserId());

        var pet = petRepository.createPet(petToCreate);

        user.getPets().add(pet);

        return pet;
    }

    @Override
    public PetDto findPetById(Long id) {
        return petRepository.findPetById(id);
    }

    @Override
    public void deletePet(Long id) {
        var pet = petRepository.findPetById(id);
        userRepository.findUserById(pet.getUserId()).getPets().remove(pet);
        petRepository.deletePetById(id);

    }
}

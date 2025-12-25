package belkin.dev.service;

import belkin.dev.model.PetDto;
import org.springframework.stereotype.Service;

@Service
public interface PetService {
    PetDto createPet(PetDto petToCreate);

    PetDto findPetById(Long id);

    void deletePet(Long id);

    PetDto updatePet(Long id, PetDto pet);

}

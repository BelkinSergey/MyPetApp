package belkin.dev.repository;

import belkin.dev.model.PetDto;

public interface PetRepository {
    void deletePetById(Long id);

    PetDto createPet(PetDto petToCreate);

    PetDto findPetById(Long id);

    PetDto updatePet(Long id, PetDto pet);


}

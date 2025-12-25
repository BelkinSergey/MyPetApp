package belkin.dev.repository;

import belkin.dev.model.PetDto;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Repository
public class ImplPetRepository implements PetRepository {
    private final Map<Long, PetDto> petMap;

    private Long idCounter = 0L;

    public ImplPetRepository() {
        this.petMap = new HashMap<>();
    }


    @Override
    public void deletePetById(Long id) {
        if (petMap.get(id) == null) {
            throw new NoSuchElementException("зверь с id " + id + "не найден!");
        }
        petMap.remove(id);
    }

    @Override
    public PetDto createPet(PetDto petToCreate) {

        var id = ++idCounter;
        var newPet = new PetDto(id,
                petToCreate.getName(),
                petToCreate.getUserId());

        petMap.put(id, newPet);
        return newPet;
    }

    @Override
    public PetDto findPetById(Long id) {
        PetDto pet = petMap.get(id);
        if (pet == null) {
            throw new NoSuchElementException("зверь с id " + id + " не найден!");
        }
        return pet;
    }

    @Override
    public PetDto updatePet(Long id, PetDto pet) {
        var newPet = new PetDto(id,
                pet.getName(),
                pet.getUserId());
        petMap.put(id, newPet);

        return newPet;
    }


}

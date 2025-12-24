package belkin.dev.controller;

import belkin.dev.model.PetDto;
import belkin.dev.service.PetService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/pets")
public class PetController {

    private final PetService petService;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public ResponseEntity<PetDto> createPet(@RequestBody @Valid PetDto petToCreate) {
        log.info("Get request for create pet: pet = {}", petToCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(petService.createPet(petToCreate));
    }

    @GetMapping("/{id}")
    public PetDto findPetById(@PathVariable("id") Long id) {
        log.info("Get request for find pet by id: id{}", id);
        return petService.findPetById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePetById(@Valid @PathVariable Long id) {
        log.info("Удаляем зверя по id {}", id);
        petService.deletePet(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

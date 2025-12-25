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
        log.info("получваем запрос на создание животного: pet = {}", petToCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(petService.createPet(petToCreate));
    }

    @GetMapping("/{id}")
    public PetDto findPetById(@PathVariable("id") Long id) {
        log.info("получвем запрос на поиск животного по id: id{}", id);
        return petService.findPetById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePetById(@Valid @PathVariable Long id) {
        log.info("получвем запрос на удаление животного по id {}", id);
        petService.deletePet(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetDto> updatePet(@Valid @PathVariable("id") Long id, @Valid @RequestBody PetDto pet) {
        log.info("получаем запрос на обноевление животного по id {}, данные {}", id, pet);
        return ResponseEntity.status(HttpStatus.OK)
                .body(petService.updatePet(id, pet));
    }

}

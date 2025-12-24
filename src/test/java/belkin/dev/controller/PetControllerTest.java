package belkin.dev.controller;

import belkin.dev.model.PetDto;
import belkin.dev.model.UserDto;
import belkin.dev.repository.ImplPetRepository;
import belkin.dev.repository.ImplUserRepository;
import belkin.dev.service.PetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PetService PetService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private ImplUserRepository implUserRepository;
    @Autowired
    private ImplPetRepository implPetRepository;


    @Test
    void shouldSuccessCreatePet() throws Exception {
        var user = new UserDto(null,
                "Andre",
                "Andre@mail.ru",
                24,
                new ArrayList<>()

        );
        var newUser = implUserRepository.createUser(user);

        var pet = new PetDto(null,
                "Bobik",
                newUser.getId()
        );


        String petJson = objectMapper.writeValueAsString(pet);

        String createdPetJson = mockMvc.perform(post("/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(petJson)
                )
                .andExpect(status().is(201))
                .andReturn()
                .getResponse()
                .getContentAsString();

        PetDto petResponse = objectMapper.readValue(createdPetJson, PetDto.class);

        var userResult = implUserRepository.findUserById(newUser.getId());
        List<PetDto> petResult = userResult.getPets();

        Assertions.assertEquals(1, petResult.size());

        Assertions.assertNotNull(petResponse.getId());
        Assertions.assertEquals(pet.getName(), petResponse.getName());
    }

    @Test
    void shouldCorrectDeletePet() throws Exception {

        var user = new UserDto(null,
                "Andre",
                "Andre@mail.ru",
                24,
                new ArrayList<>()

        );
        var newUser = implUserRepository.createUser(user);

        var pet = new PetDto(null,
                "Bobik",
                newUser.getId()
        );

        var newPet = implPetRepository.createPet(pet);

        mockMvc.perform(delete("/pets/{id}", newPet.getId()))
                .andExpect(status().isNoContent());


        var userResult = implUserRepository.findUserById(newUser.getId());
        List<PetDto> petResult = userResult.getPets();

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            implPetRepository.findPetById(newPet.getId());
        });
        Assertions.assertEquals(0, petResult.size());
    }
}


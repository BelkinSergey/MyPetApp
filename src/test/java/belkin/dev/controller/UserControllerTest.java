package belkin.dev.controller;

import belkin.dev.model.UserDto;
import belkin.dev.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldSuccessCreateUser() throws Exception {
        var user = new UserDto(null,
                "Andrew",
                "Andrew@mail.ru",
                24,
                new ArrayList<>()
        );
        String userJson = objectMapper.writeValueAsString(user);

        String createdUserJson = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
                )
                .andExpect(status().is(201))
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDto userResponse = objectMapper.readValue(createdUserJson, UserDto.class);

        Assertions.assertNotNull(userResponse.getId());
        Assertions.assertEquals(user.getName(), userResponse.getName());
    }

    @Test
    void shouldNotCreateUserWhenRequestNotValid() throws Exception {
        var user = new UserDto(null,
                null,
                "Andrew@mail.ru",
                20,
                new ArrayList<>()
        );
        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
                )
                .andExpect(status().is(400));
    }

    @Test
    void shouldReturnCorrectUserById() throws Exception {
        var user = new UserDto(null,
                "Andrew",
                "Andrew@mail.ru",
                20,
                new ArrayList<>()
        );

        var newUser = userService.createUser(user);

        String result = mockMvc.perform(get("/users/{id}", newUser.getId()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDto userResponse = objectMapper.readValue(result, UserDto.class);

        Assertions.assertNotNull(userResponse);
        Assertions.assertEquals(user.getName(), userResponse.getName());
    }
}

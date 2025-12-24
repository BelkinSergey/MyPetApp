package belkin.dev.controller;

import belkin.dev.model.UserDto;
import belkin.dev.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userToCreate) {
        log.info("Get request for create user: user = {}", userToCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userToCreate));
    }

    @GetMapping("/{id}")
    public UserDto findUserById(@PathVariable("id") Long id) {
        log.info("Get request for find user by id: id{}", id);
        return userService.findUserById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@Valid @PathVariable("id") Long id, @Valid @RequestBody UserDto user) {
        log.info("Обновляем пользователя по id {}, данные {}", id, user);
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.updateUser(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@Valid @PathVariable Long id) {
        log.info("Удаляем пользователя по id {}", id);
        userService.deleteUser(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}

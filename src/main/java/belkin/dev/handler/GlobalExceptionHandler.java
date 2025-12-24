package belkin.dev.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(MethodArgumentNotValidException.class) // данный метод используется в качестве обработчика ошибок
    public ResponseEntity<ServerErrorDto> handleValidationException(MethodArgumentNotValidException e) {
        log.error("Got validation exception", e);

        String detailedMessage = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        var errorDto = new ServerErrorDto("Ошибка валидации запроса", detailedMessage, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorDto);
    }

    @ExceptionHandler // данный метод используется в качестве обработчика ошибок
    public ResponseEntity<ServerErrorDto> handleGenericException(Exception e) {
        log.error("Server error", e);
        var errorDto = new ServerErrorDto("Server error", e.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorDto);
    }

    @ExceptionHandler(NoSuchElementException.class) // данный метод используется в качестве обработчика ошибок
    public ResponseEntity<ServerErrorDto> handleNotFoundException(NoSuchElementException e) {
        log.error("Got Exception", e);
        var errorDto = new ServerErrorDto("сущность не найдена", e.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorDto);
    }

    @ExceptionHandler(IllegalArgumentException.class) // данный метод используется в качестве обработчика ошибок
    public ResponseEntity<ServerErrorDto> handleNotFoundException(IllegalArgumentException e) {
        log.error("Got Exception", e);
        var errorDto = new ServerErrorDto("сущность не найдена", e.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorDto);
    }
}


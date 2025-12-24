package belkin.dev.handler;

import java.time.LocalDateTime;

public record ServerErrorDto(String message, String detailMessage, LocalDateTime dateTime) {


}
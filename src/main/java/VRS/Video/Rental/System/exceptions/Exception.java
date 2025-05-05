package VRS.Video.Rental.System.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
public class Exception {
    private final String message;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;

    public Exception(String message, HttpStatus httpStatus, ZonedDateTime timestamp) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
    }

}

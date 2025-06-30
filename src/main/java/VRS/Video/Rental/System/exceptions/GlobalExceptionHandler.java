package VRS.Video.Rental.System.exceptions;

import VRS.Video.Rental.System.exceptions.customerExceptions.CustomerAlreadyExistsException;
import VRS.Video.Rental.System.exceptions.customerExceptions.CustomerNotFoundException;
import VRS.Video.Rental.System.exceptions.customerExceptions.InsufficientFundsException;
import VRS.Video.Rental.System.exceptions.videoExceptions.OutOfStockException;
import VRS.Video.Rental.System.exceptions.videoExceptions.VideoAlreadyExistsException;
import VRS.Video.Rental.System.exceptions.videoExceptions.VideoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {GlobalRuntimeException.class})
    public ResponseEntity<Object> handleRequestException(GlobalRuntimeException e){
        Exception z = new Exception(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(z, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = {VideoAlreadyExistsException.class})
    public ResponseEntity<Object> handleVideoAlreadyExistsException(VideoAlreadyExistsException e){
        Exception z = new Exception(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
                return new ResponseEntity<>(z, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = {CustomerAlreadyExistsException.class})
    public ResponseEntity<Object> handleCustomerAlreadyExists(CustomerAlreadyExistsException e){
        Exception z = new Exception(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
                return new ResponseEntity<>(z, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = {CustomerNotFoundException.class})
    public ResponseEntity<Object> handleCustomerNotFound(CustomerNotFoundException e){
        Exception z = new Exception(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(z, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = {InsufficientFundsException.class})
    public ResponseEntity<Object> handleInsufficientFundsException(InsufficientFundsException e){
        Exception z = new Exception(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(z, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = {OutOfStockException.class})
    public ResponseEntity<Object> handleOutOfStockException(OutOfStockException e){
        Exception z = new Exception(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(z, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = {VideoNotFoundException.class})
    public ResponseEntity<Object> handleVideoNotFoundException(VideoNotFoundException e){
        Exception z = new Exception(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(z, HttpStatus.BAD_REQUEST);
    }
}

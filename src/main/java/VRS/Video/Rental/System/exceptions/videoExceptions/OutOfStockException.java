package VRS.Video.Rental.System.exceptions.videoExceptions;

public class OutOfStockException extends RuntimeException {
    public OutOfStockException(String message) {
        super(message);
    }
}

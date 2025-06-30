package VRS.Video.Rental.System.exceptions.videoExceptions;

public class VideoAlreadyExistsException extends RuntimeException {
    public VideoAlreadyExistsException(String message) {
        super(message);
    }
}

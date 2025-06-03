package VRS.Video.Rental.System.exceptions.videoExceptions;

public class VideoNotFoundException extends RuntimeException {
    public VideoNotFoundException(String message) {
        super(message);
    }
}

package VRS.Video.Rental.System.exceptions.managerExceptions;

public class UserIsNotManagerException extends RuntimeException {
    public UserIsNotManagerException(String message) {
        super(message);
    }
}

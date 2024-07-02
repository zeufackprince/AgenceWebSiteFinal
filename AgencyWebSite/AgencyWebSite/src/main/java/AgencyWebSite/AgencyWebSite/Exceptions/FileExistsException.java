package AgencyWebSite.AgencyWebSite.Exceptions;

public class FileExistsException extends RuntimeException {
    public FileExistsException(String message) {
        super(message);
    }
}

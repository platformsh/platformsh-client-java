package sh.platform.client;

/**
 * The Platform.sh API root exception
 */
public class PlatformClientException extends RuntimeException {

    public PlatformClientException(String message) {
        super(message);
    }

    public PlatformClientException(String message, Throwable cause) {
        super(message, cause);
    }
}

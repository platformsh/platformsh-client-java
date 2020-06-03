package sh.platform.client;

import org.apache.http.StatusLine;

import java.util.Optional;

/**
 * The Platform.sh API root exception
 */
public class PlatformClientException extends RuntimeException {

    private StatusLine statusLine;

    public PlatformClientException(StatusLine statusLine, String url) {
        super("There is an error on the request url " + url
                + " http return: " + statusLine.getStatusCode() + " "
                + statusLine.getReasonPhrase());
        this.statusLine = statusLine;
    }

    public PlatformClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public Optional<StatusLine> getStatus() {
        return Optional.ofNullable(statusLine);
    }
}

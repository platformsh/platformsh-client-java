package sh.platform.config;

/**
 * Platform.SH has all configuration setup through the environment in the cloud,
 * but the Java API offers a fallback file that is useful when we talk about local development.
 * This exception will handle when there is some error when trying this fallback process.
 */
class FallbackException extends PlatformShException {

    /**
     * Constructs a new runtime exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this runtime exception's detail message.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link #getCause()} method).  (A <tt>null</tt> value is
     *                permitted, and indicates that the cause is nonexistent or
     *                unknown.)
     */
    FallbackException(String message, Throwable cause) {
        super(message, cause);
    }
}

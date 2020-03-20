package sh.platform.config;

/**
 * Thrown by a {@link Credential} instance when a property is not found
 */
class PropertyNotFoundException extends PlatformShException {

    /** Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param   message   the detail message. The detail message is saved for
     *          later retrieval by the {@link #getMessage()} method.
     */
    PropertyNotFoundException(String message) {
        super(message);
    }
}

package es.uclm.esi.iso2.greenhouse;

/**
 * Exception thrown when a sensor reading is null or physically impossible.
 */
public class InvalidSensorReadingException extends IllegalArgumentException {
    private static final long serialVersionUID = 1L;

    /**
     * Creates an exception describing the invalid sensor value.
     *
     * @param message description of the validation failure
     */
    public InvalidSensorReadingException(String message) {
        super(message);
    }
}

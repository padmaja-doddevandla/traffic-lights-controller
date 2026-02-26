package com.example.traffic.exception;

/**
 * Exception thrown when an operation is attempted while the traffic
 * intersection system is paused.
 *
 * <p>This is a runtime exception and is typically handled globally
 * by {@link GlobalExceptionHandler} to return an appropriate HTTP
 * response.</p>
 *
 * <p>Example scenario:</p>
 * <pre>
 *     {@code
 *     if (paused) {
 *         throw new IntersectionPausedException("System is paused");
 *     }
 *     }
 * </pre>
 */
public class IntersectionPausedException extends RuntimeException {
	/**
     * Constructs a new {@code IntersectionPausedException} with the
     * specified detail message.
     *
     * @param message the detail message explaining why the exception was thrown
     */
    public IntersectionPausedException(String message) {
        super(message);
    }
}

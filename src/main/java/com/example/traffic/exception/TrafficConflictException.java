package com.example.traffic.exception;

/**
 * Exception thrown when a traffic light conflict occurs at the intersection.
 *
 * <p>This exception is thrown when an attempt is made to set a traffic light
 * to GREEN while another conflicting direction is already GREEN.</p>
 *
 * <p>This is a runtime exception and is typically handled globally by
 * {@link GlobalExceptionHandler} to return an appropriate HTTP 409 CONFLICT response.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 *     {@code
 *     if (anotherDirectionIsGreen) {
 *         throw new TrafficConflictException("Conflict detected: North is already GREEN");
 *     }
 *     }
 * </pre>
 */
public class TrafficConflictException extends RuntimeException {
	
	 /**
     * Constructs a new {@code TrafficConflictException} with the specified detail message.
     *
     * @param message the detail message explaining the conflict
     */
    public TrafficConflictException(String message) {
        super(message);
    }
}
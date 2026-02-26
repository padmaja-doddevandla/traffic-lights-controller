package com.example.traffic.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

/**
 * Global exception handler for the Traffic light system.
 *
 * <p>This class intercepts exceptions thrown by controllers and provides
 * consistent HTTP responses for different error scenarios.</p>
 *
 * <p>Handled Exceptions:</p>
 * <ul>
 *     <li>{@link TrafficConflictException} → Returns HTTP 409 CONFLICT</li>
 *     <li>{@link IntersectionPausedException} → Returns HTTP 400 BAD REQUEST</li>
 *     <li>{@link Exception} → Returns HTTP 500 INTERNAL SERVER ERROR for all other exceptions</li>
 * </ul>
 *
 * <p>Each response includes a timestamp and a human-readable message.</p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
     * Handles {@link TrafficConflictException} thrown when conflicting
     * traffic lights are requested.
     *
     * @param ex the thrown {@link TrafficConflictException}
     * @return {@link ErrorResponse} containing the message and timestamp
     */
    @ExceptionHandler(TrafficConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleConflict(TrafficConflictException ex) {
        return new ErrorResponse(ex.getMessage(), Instant.now());
    }

    /**
     * Handles {@link IntersectionPausedException} thrown when an operation
     * is attempted while the intersection system is paused.
     *
     * @param ex the thrown {@link IntersectionPausedException}
     * @return {@link ErrorResponse} containing the message and timestamp
     */
    @ExceptionHandler(IntersectionPausedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlePaused(IntersectionPausedException ex) {
        return new ErrorResponse(ex.getMessage(), Instant.now());
    }

    /**
     * Handles all other unhandled exceptions.
     *
     * <p>This method provides a generic error response for unexpected errors.</p>
     *
     * @param ex the thrown {@link Exception}
     * @return {@link ErrorResponse} containing a generic message and timestamp
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGeneric(Exception ex) {
        return new ErrorResponse("Unexpected error", Instant.now());
    }
    
    /**
     * Represents a standardized error response returned to clients.
     *
     * @param message   the human-readable error message
     * @param timestamp the time at which the error occurred
     */
    public record ErrorResponse(String message, Instant timestamp) {}
}
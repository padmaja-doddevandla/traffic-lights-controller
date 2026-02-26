package com.example.traffic.domain;

import lombok.Data;


/**
 * Represents a request to change the state of a traffic light.
 *
 * <p>This class contains the following information:</p>
 * <ul>
 *     <li>{@link #direction} – the direction of the traffic light to change</li>
 *     <li>{@link #color} – the new color to set for the traffic light</li>
 * </ul>
 *
 * <p>This class uses Lombok's {@link Data} annotation to automatically generate:</p>
 * <ul>
 *     <li>Getters and setters for all fields</li>
 *     <li>toString(), equals(), and hashCode() methods</li>
 * </ul>
 *
 * <p>Example usage:</p>
 * <pre>
 *     {@code
 *     ChangeRequest request = new ChangeRequest();
 *     request.setDirection(Directions.NORTH);
 *     request.setColor(LightColors.GREEN);
 *     }
 * </pre>
 */
@Data
class ChangeRequest {
	
    private Directions direction;
    private LightColors color;
}
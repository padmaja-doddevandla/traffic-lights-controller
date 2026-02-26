package com.example.traffic.domain;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @LightChangeRequest class
 */

@Data
public class LightChangeRequest {

	 /**
     * The direction of the traffic light to be changed.
     */
    @NotNull
    private Directions direction;

    /**
     * The new color to set for the specified traffic light.
     */
    @NotNull
    private LightColors color;

	public Directions getDirection() {
		return direction;
	}

	public void setDirection(Directions direction) {
		this.direction = direction;
	}

	public LightColors getColor() {
		return color;
	}

	public void setColor(LightColors color) {
		this.color = color;
	}

	
    
}
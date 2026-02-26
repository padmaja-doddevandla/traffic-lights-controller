package com.example.traffic.domain;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LightChangeRequest {

    @NotNull
    private Direction direction;

    @NotNull
    private LightColor color;

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public LightColor getColor() {
		return color;
	}

	public void setColor(LightColor color) {
		this.color = color;
	}
    
    
}
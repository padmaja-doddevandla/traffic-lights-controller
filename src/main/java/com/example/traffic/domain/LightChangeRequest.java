package com.example.traffic.domain;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LightChangeRequest {

    @NotNull
    private Directions direction;

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
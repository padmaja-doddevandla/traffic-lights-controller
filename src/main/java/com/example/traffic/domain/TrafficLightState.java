package com.example.traffic.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TrafficLightState {

	private Directions direction;
	private LightColors color;
	private long timestamp;
}
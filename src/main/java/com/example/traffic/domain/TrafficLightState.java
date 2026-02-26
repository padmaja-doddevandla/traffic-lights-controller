package com.example.traffic.domain;

import lombok.AllArgsConstructor;
import lombok.Data;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TrafficLightState {

    private Direction direction;
    private LightColor color;
    private long timestamp;
}
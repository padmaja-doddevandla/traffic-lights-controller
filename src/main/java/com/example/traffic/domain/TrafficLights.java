package com.example.traffic.domain;


import java.time.Instant;

public class TrafficLights {

    private LightColors color;
    private Instant lastChanged;

    public TrafficLights() {
        this.color = LightColors.RED;
        this.lastChanged = Instant.now();
    }

    public synchronized void change(LightColors newColor) {
        this.color = newColor;
        this.lastChanged = Instant.now();
    }

    public LightColors getColor() {
        return color;
    }

    public Instant getLastChanged() {
        return lastChanged;
    }
}
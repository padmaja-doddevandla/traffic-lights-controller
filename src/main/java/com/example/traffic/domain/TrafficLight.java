package com.example.traffic.domain;


import java.time.Instant;

public class TrafficLight {

    private LightColor color;
    private Instant lastChanged;

    public TrafficLight() {
        this.color = LightColor.RED;
        this.lastChanged = Instant.now();
    }

    public synchronized void change(LightColor newColor) {
        this.color = newColor;
        this.lastChanged = Instant.now();
    }

    public LightColor getColor() {
        return color;
    }

    public Instant getLastChanged() {
        return lastChanged;
    }
}
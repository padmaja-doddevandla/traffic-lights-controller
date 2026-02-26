package com.example.traffic.exception;


public class TrafficConflictException extends RuntimeException {
    public TrafficConflictException(String message) {
        super(message);
    }
}
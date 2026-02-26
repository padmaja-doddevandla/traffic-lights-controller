package com.example.traffic.exception;


public class IntersectionPausedException extends RuntimeException {
    public IntersectionPausedException(String message) {
        super(message);
    }
}

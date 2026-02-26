package com.example.traffic.domain;

import java.time.LocalDateTime;

/**
 * Represents a record of a traffic light change or system event.
 **/ 
public class HistoryEntry {

	/**
     * Descriptive message for the event.
     */
    private String message;
    
    /**
     * Timestamp when the event occurred.
     */
    private LocalDateTime timestamp;

    public HistoryEntry(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

    
}
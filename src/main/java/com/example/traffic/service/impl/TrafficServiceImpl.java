package com.example.traffic.service.impl;


import com.example.traffic.domain.Directions;
import com.example.traffic.domain.HistoryEntry;

import com.example.traffic.domain.LightColors;
import com.example.traffic.exception.IntersectionPausedException;
import com.example.traffic.exception.TrafficConflictException;
import com.example.traffic.service.TrafficService;
import org.springframework.stereotype.Service;

import java.util.Map;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.*;


/**
 * Service implementation responsible for managing traffic light operations
 * at an intersection.
 *
 * <p>This class maintains:</p>
 * <ul>
 *     <li>The current light state for each direction</li>
 *     <li>The history of traffic changes and system events</li>
 *     <li>The operational state of the intersection (paused/resumed)</li>
 * </ul>
 * */
@Service
public class TrafficServiceImpl implements TrafficService {

    private final Map<Directions, LightColors> currentState = new ConcurrentHashMap<>();
    private final List<HistoryEntry> history = new CopyOnWriteArrayList<>();
    private volatile boolean paused = false;

    public TrafficServiceImpl() {
        for (Directions dir : Directions.values()) {
            currentState.put(dir, LightColors.RED);
        }
    }
    
    /**
     * Changes the traffic light color for a specific direction.
     *
     * <p>Performs the following validations:</p>
     * <ul>
     *     <li>Throws {@link IntersectionPausedException} if the system is paused</li>
     *     <li>Ensures no conflicting direction is GREEN</li>
     * </ul>
     *
     * @param direction the direction whose light needs to change
     * @param color     the new light color
     * @throws IntersectionPausedException if the system is paused
     * @throws TrafficConflictException    if another direction is already GREEN
     */

    @Override
    public synchronized void changeLight(Directions direction, LightColors color) {

        if (paused) {
            throw new IntersectionPausedException("System is paused");
        }

        // Conflict validation
        if (color == LightColors.GREEN) {
            validateNoConflict(direction);
        }

        currentState.put(direction, color);
        history.add(new HistoryEntry(direction + " changed to " + color));
    }

    /**
     * Validates that no other direction is currently GREEN.
     *
     * @param direction the requested direction for GREEN
     * @throws TrafficConflictException if another direction is already GREEN
     */
    private void validateNoConflict(Directions direction) {
        for (Map.Entry<Directions, LightColors> entry : currentState.entrySet()) {
            if (!entry.getKey().equals(direction)
                    && entry.getValue() == LightColors.GREEN) {
            	 throw new TrafficConflictException(
                         "Conflict detected: "  +
                                 " is already GREEN.");
            }
        }
    }

    /**
     * Pauses the traffic light system.
     *
     * <p>No light changes are allowed while paused.</p>
     */
    @Override
    public void pause() {
        paused = true;
        history.add(new HistoryEntry("System Paused"));
    }
    
    /**
     * Resumes the traffic light system after being paused.
     */

    @Override
    public void resume() {
        paused = false;
        history.add(new HistoryEntry("System Resumed"));
    }

    /**
     * Retrieves the current state of all traffic lights.
     *
     * @return a map containing the current direction-to-color mapping
     */
    
    @Override
    public Map<Directions, LightColors> getCurrentState() {
        return currentState;
    }

    /**
     * Retrieves the history of traffic light changes and system events.
     *
     * @return list of {@link HistoryEntry} records
     */
   @Override
    public List<HistoryEntry> getHistory() {
        return history;
    }

  
   }


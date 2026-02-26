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

    @Override
    public void pause() {
        paused = true;
        history.add(new HistoryEntry("System Paused"));
    }

    @Override
    public void resume() {
        paused = false;
        history.add(new HistoryEntry("System Resumed"));
    }

    @Override
    public Map<Directions, LightColors> getCurrentState() {
        return currentState;
    }

   @Override
    public List<HistoryEntry> getHistory() {
        return history;
    }

  
   }


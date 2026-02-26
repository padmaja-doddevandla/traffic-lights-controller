package com.example.traffic.service;


import com.example.traffic.domain.Direction;
import com.example.traffic.domain.HistoryEntry;

import com.example.traffic.domain.LightColor;
import com.example.traffic.service.TrafficService;
import org.springframework.stereotype.Service;

import java.util.Map;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.*;

@Service
public class TrafficServiceImpl implements TrafficService {

    private final Map<Direction, LightColor> currentState = new ConcurrentHashMap<>();
    private final List<HistoryEntry> history = new CopyOnWriteArrayList<>();
    private volatile boolean paused = false;

    public TrafficServiceImpl() {
        for (Direction dir : Direction.values()) {
            currentState.put(dir, LightColor.RED);
        }
    }

    @Override
    public synchronized void changeLight(Direction direction, LightColor color) {

        if (paused) {
            throw new IllegalStateException("System is paused");
        }

        // Conflict validation
        if (color == LightColor.GREEN) {
            validateNoConflict(direction);
        }

        currentState.put(direction, color);
        history.add(new HistoryEntry(direction + " changed to " + color));
    }

    private void validateNoConflict(Direction direction) {
        for (Map.Entry<Direction, LightColor> entry : currentState.entrySet()) {
            if (!entry.getKey().equals(direction)
                    && entry.getValue() == LightColor.GREEN) {
                throw new IllegalStateException("Conflict detected: "
                        + entry.getKey() + " is already GREEN");
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
    public Map<Direction, LightColor> getCurrentState() {
        return currentState;
    }

   @Override
    public List<HistoryEntry> getHistory() {
        return history;
    }

  
   }


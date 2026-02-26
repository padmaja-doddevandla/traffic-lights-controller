package com.example.traffic.service.impl;

import com.example.traffic.domain.Directions;
import com.example.traffic.domain.HistoryEntry;
import com.example.traffic.domain.LightColors;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TrafficServiceImplTest {

    private TrafficServiceImpl trafficService;

    @BeforeEach
    void setup() {
        trafficService = new TrafficServiceImpl();
    }

    @Test
    void shouldInitializeAllDirectionsWithRed() {
        Map<Directions, LightColors> state = trafficService.getCurrentState();

        for (Directions direction : Directions.values()) {
            assertEquals(LightColors.RED, state.get(direction));
        }
    }

    @Test
    void shouldChangeLightSuccessfully() {
        trafficService.changeLight(Directions.NORTH, LightColors.GREEN);

        assertEquals(LightColors.GREEN,
                trafficService.getCurrentState().get(Directions.NORTH));
    }

    @Test
    void shouldThrowExceptionWhenSystemIsPaused() {
        trafficService.pause();

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> trafficService.changeLight(Directions.NORTH, LightColors.GREEN)
        );

        assertEquals("System is paused", exception.getMessage());
    }

    @Test
    void shouldThrowConflictExceptionWhenAnotherDirectionIsGreen() {
        trafficService.changeLight(Directions.NORTH, LightColors.GREEN);

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> trafficService.changeLight(Directions.SOUTH, LightColors.GREEN)
        );

        assertTrue(exception.getMessage().contains("Conflict detected"));
    }

    @Test
    void shouldPauseSystem() {
        trafficService.pause();

        assertThrows(IllegalStateException.class,
                () -> trafficService.changeLight(Directions.NORTH, LightColors.GREEN));
    }

    @Test
    void shouldResumeSystem() {
        trafficService.pause();
        trafficService.resume();

        assertDoesNotThrow(() ->
                trafficService.changeLight(Directions.NORTH, LightColors.GREEN));
    }

    @Test
    void shouldAddHistoryOnChange() {
        trafficService.changeLight(Directions.NORTH, LightColors.GREEN);

        List<HistoryEntry> history = trafficService.getHistory();

        assertFalse(history.isEmpty());
        assertTrue(history.get(0).toString().contains("NORTH changed to GREEN"));
    }

    @Test
    void shouldRecordPauseAndResumeInHistory() {
        trafficService.pause();
        trafficService.resume();

        List<HistoryEntry> history = trafficService.getHistory();

        assertEquals("System Paused", history.get(0).toString());
        assertEquals("System Resumed", history.get(1).toString());
    }
}
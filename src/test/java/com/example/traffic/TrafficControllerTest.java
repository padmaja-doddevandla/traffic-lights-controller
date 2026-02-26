package com.example.traffic;

import com.example.traffic.controller.TrafficController;
import com.example.traffic.domain.Direction;
import com.example.traffic.domain.LightChangeRequest;
import com.example.traffic.domain.LightColor;
import com.example.traffic.service.TrafficService;

import tools.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TrafficController.class)
class TrafficControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TrafficService trafficService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldChangeLightSuccessfully() throws Exception {

        LightChangeRequest request = new LightChangeRequest();
        request.setDirection(Direction.NORTH);
        request.setColor(LightColor.GREEN);

        mockMvc.perform(post("/api/traffic/change")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Light changed successfully"));

        verify(trafficService)
                .changeLight(Direction.NORTH, LightColor.GREEN);
    }

    @Test
    void shouldPauseSystem() throws Exception {

        mockMvc.perform(post("/api/traffic/pause"))
                .andExpect(status().isOk())
                .andExpect(content().string("Paused"));

        verify(trafficService).pause();
    }

    @Test
    void shouldResumeSystem() throws Exception {

        mockMvc.perform(post("/api/traffic/resume"))
                .andExpect(status().isOk())
                .andExpect(content().string("System resumed"));

        verify(trafficService).resume();
    }

    @Test
    void shouldReturnCurrentState() throws Exception {

        Mockito.when(trafficService.getCurrentState())
                .thenReturn(Map.of(Direction.NORTH, LightColor.GREEN));

        mockMvc.perform(get("/api/traffic/state"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.NORTH").value("GREEN"));
    }

    @Test
    void shouldReturnHistory() throws Exception {

        Mockito.when(trafficService.getHistory())
                .thenReturn(List.of());

        mockMvc.perform(get("/api/traffic/history"))
                .andExpect(status().isOk());
    }
}
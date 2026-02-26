package com.example.traffic;

import com.example.traffic.controller.TrafficController;
import com.example.traffic.domain.Directions;
import com.example.traffic.domain.LightChangeRequest;
import com.example.traffic.domain.LightColors;
import com.example.traffic.exception.TrafficConflictException;
import com.example.traffic.service.TrafficService;
import tools.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TrafficController.class)
class TrafficControllerTest {

	@Mock
	private MockMvc mockMvc;

	@Mock
	private TrafficService trafficService;

	@Mock
	private ObjectMapper objectMapper;

	@Test
	void shouldChangeLightSuccessfully() throws Exception {

		String requestJson = """
				{
				  "direction": "NORTH",
				  "color": "GREEN"
				}
				""";

		mockMvc.perform(post("/api/traffic/change").contentType(MediaType.APPLICATION_JSON).content(requestJson))
				.andExpect(status().isOk()).andExpect(content().string("Light changed successfully"));

		verify(trafficService).changeLight(Directions.NORTH, LightColors.GREEN);
	}

	@Test
	@DisplayName("Should return 409 when TrafficConflictException occurs")
	void shouldReturnConflictWhenExceptionThrown() throws Exception {

		doThrow(new TrafficConflictException("Conflict detected")).when(trafficService).changeLight(any(), any());

		String requestJson = """
				{
				  "direction": "NORTH",
				  "color": "GREEN"
				}
				""";

		mockMvc.perform(post("/api/traffic/change").contentType(MediaType.APPLICATION_JSON).content(requestJson))
				.andExpect(status().isConflict()).andExpect(content().string("Conflict detected"));
	}

	@Test
	void shouldPauseSystem() throws Exception {

		mockMvc.perform(post("/api/traffic/pause")).andExpect(status().isOk()).andExpect(content().string("Paused"));

		verify(trafficService).pause();
	}

	@Test
	void shouldResumeSystem() throws Exception {

		mockMvc.perform(post("/api/traffic/resume")).andExpect(status().isOk())
				.andExpect(content().string("System resumed"));

		verify(trafficService).resume();
	}

	@Test
	void shouldReturnState() throws Exception {

		when(trafficService.getCurrentState()).thenReturn(Map.of(Directions.NORTH, LightColors.RED));

		mockMvc.perform(get("/api/traffic/state")).andExpect(status().isOk())
				.andExpect(jsonPath("$.NORTH").value("RED"));
	}

	@Test
	void shouldReturnHistory() throws Exception {

		when(trafficService.getHistory()).thenReturn(List.of());

		mockMvc.perform(get("/api/traffic/history")).andExpect(status().isOk());

		verify(trafficService).getHistory();
	}
}
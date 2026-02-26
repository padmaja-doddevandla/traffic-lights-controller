package com.example.traffic.controller;


import com.example.traffic.domain.LightChangeRequest;
import com.example.traffic.exception.TrafficConflictException;
import com.example.traffic.service.TrafficService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/traffic")
public class TrafficController {

	@Autowired
    private final TrafficService service;

	
	  public TrafficController(TrafficService service) { this.service = service; }
	
	  
	  @PostMapping("/change")
	  public ResponseEntity<?> changeLight(@RequestBody LightChangeRequest request) {
	      try {
	          service.changeLight(request.getDirection(), request.getColor());
	          return ResponseEntity.ok("Light changed successfully");
	      } catch (TrafficConflictException ex) {
	          return ResponseEntity.status(HttpStatus.CONFLICT)
	                  .body(ex.getMessage());
	      }
	  }

	  @PostMapping("/pause")
	    public ResponseEntity<String> pause() {
	        service.pause();
	        return ResponseEntity.ok("Paused");
	    }

	    @PostMapping("/resume")
	    public ResponseEntity<String> resume() {
	        service.resume();
	        return ResponseEntity.ok("System resumed");
	    }

	    @GetMapping("/state")
	    public ResponseEntity<?> getState() {
	        return ResponseEntity.ok(service.getCurrentState());
	    }

	    @GetMapping("/history")
	    public ResponseEntity<?> getHistory() {
	        return ResponseEntity.ok(service.getHistory());
	    }
}
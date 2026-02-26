package com.example.traffic.controller;


import com.example.traffic.domain.LightChangeRequest;

import com.example.traffic.service.TrafficService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/traffic")
public class TrafficController {

	@Autowired
    private final TrafficService service;

	
	  public TrafficController(TrafficService service) { this.service = service; }
	

	  
		/*
		 * @PostMapping("/change") public String changeLight(@RequestParam Direction
		 * direction,
		 * 
		 * @RequestParam LightColor color) {
		 * 
		 * service.changeLight(direction, color); return "Light updated successfully"; }
		 */
    
	  @PostMapping("/change")
	    public String changeLight(@RequestBody LightChangeRequest request) {
		  service.changeLight(request.getDirection(), request.getColor());
	        return "Light changed successfully";
	    }

	  @PostMapping("/pause")
	    public ResponseEntity<?> pause() {
	        service.pause();
	        return ResponseEntity.ok("Paused");
	    }

    @PostMapping("/resume")
    public String resume() {
        service.resume();
        return "System resumed";
    }

    @GetMapping("/state")
    public Object getState() {
        return service.getCurrentState();
    }
    @GetMapping("/history")
    public Object getHistory() {
        return service.getHistory();
    }
}
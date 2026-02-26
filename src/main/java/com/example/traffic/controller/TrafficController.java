package com.example.traffic.controller;


import com.example.traffic.domain.LightChangeRequest;
import com.example.traffic.exception.TrafficConflictException;
import com.example.traffic.service.TrafficService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller responsible for managing traffic light operations.
 *
 * <p>This controller exposes endpoints to:</p>
 * <ul>
 *     <li>Change traffic light state</li>
 *     <li>Pause traffic light operations</li>
 *     <li>Resume traffic light operations</li>
 *     <li>Retrieve current traffic state</li>
 *     <li>Retrieve traffic light change history</li>
 * </ul>
 *
 * <p>All business logic is delegated to {@link TrafficService}.</p>
 *
 * <p>
 * If a conflicting traffic light change is attempted (e.g., two conflicting directions
 * turning GREEN simultaneously), a {@link TrafficConflictException} is caught
 * and mapped to HTTP 409 (CONFLICT).
 * </p>
 */
@RestController
@RequestMapping("/api/traffic")
public class TrafficController {

	@Autowired
    private final TrafficService service;

	 /**
     * Constructs a TrafficController with the required TrafficService dependency.
     *
     * @param service the traffic service responsible for business logic
     */
	  public TrafficController(TrafficService service) { this.service = service; }
	
	  /**
	     * Changes the traffic light state for a given direction.
	     *
	     * @param request the request containing direction and light color
	     * @return HTTP 200 with success message if the light is changed successfully,
	     *         or HTTP 409 if a traffic conflict occurs
	     */
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

	  /**
	     * Pauses the traffic light system.
	     *
	     * @return HTTP 200 with confirmation message indicating the system is paused
	     */
	  @PostMapping("/pause")
	    public ResponseEntity<String> pause() {
	        service.pause();
	        return ResponseEntity.ok("Paused");
	    }

	  /**
	     * Resumes the traffic light system if it was previously paused.
	     *
	     * @return HTTP 200 with confirmation message indicating the system is resumed
	     */
	    @PostMapping("/resume")
	    public ResponseEntity<String> resume() {
	        service.resume();
	        return ResponseEntity.ok("System resumed");
	    }
	    
	    /**
	     * Retrieves the current state of all traffic lights.
	     *
	     * @return HTTP 200 containing the current traffic light states
	     */

	    @GetMapping("/state")
	    public ResponseEntity<?> getState() {
	        return ResponseEntity.ok(service.getCurrentState());
	    }
	    
	    /**
	     * Retrieves the history of traffic light state changes.
	     *
	     * @return HTTP 200 containing the traffic light change history
	     */

	    @GetMapping("/history")
	    public ResponseEntity<?> getHistory() {
	        return ResponseEntity.ok(service.getHistory());
	    }
}
package com.example.traffic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Entry point for the Traffic Lights Controller Spring Boot application.
 *
 * <p>This class is annotated with {@link SpringBootApplication}, which enables:</p>
 * <ul>
 *     <li>Component scanning of the {@code com.example.traffic} package and sub-packages</li>
 *     <li>Auto-configuration of Spring Boot features</li>
 *     <li>Configuration of embedded web server for running the REST APIs</li>
 * </ul>
 *
 * <p>Running this class will start the application and expose the traffic control REST endpoints.</p>
 *
 *
 */
@SpringBootApplication
public class TrafficLightsControllerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrafficLightsControllerApplication.class, args);
	}

}

package com.example.traffic.domain;


/**
 * Enum representing the four possible directions at an intersection.
 *
 * <p>Used to identify the direction of a traffic light for
 * controlling and tracking traffic flow.</p>
 *
 * <ul>
 *     <li>{@link #NORTH} – represents the northbound direction</li>
 *     <li>{@link #SOUTH} – represents the southbound direction</li>
 *     <li>{@link #EAST} – represents the eastbound direction</li>
 *     <li>{@link #WEST} – represents the westbound direction</li>
 * </ul>
 *
 */
public enum Directions {
    NORTH, SOUTH, EAST, WEST
}
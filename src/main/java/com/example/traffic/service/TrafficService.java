package com.example.traffic.service;


import com.example.traffic.domain.Directions;
import com.example.traffic.domain.HistoryEntry;
import com.example.traffic.domain.LightColors;

import java.util.Map;

import java.util.List;

public interface TrafficService {

    void changeLight(Directions direction, LightColors color);

    void pause();

    void resume();

    Map<Directions, LightColors> getCurrentState();

    List<HistoryEntry> getHistory();

}
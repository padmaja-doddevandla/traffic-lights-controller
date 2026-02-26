package com.example.traffic.service;


import com.example.traffic.domain.Direction;
import com.example.traffic.domain.HistoryEntry;
import com.example.traffic.domain.LightColor;

import java.util.Map;

import java.util.List;

public interface TrafficService {

    void changeLight(Direction direction, LightColor color);

    void pause();

    void resume();

    Map<Direction, LightColor> getCurrentState();

    List<HistoryEntry> getHistory();

	//void changeLight(Map<Direction, LightColor> changes);
}
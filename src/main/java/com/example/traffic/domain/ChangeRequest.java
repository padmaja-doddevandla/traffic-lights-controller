package com.example.traffic.domain;

import lombok.Data;

@Data
class ChangeRequest {
    private Directions direction;
    private LightColors color;
}
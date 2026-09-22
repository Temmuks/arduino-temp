package com.example.arduino_temp_checker_server.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.arduino_temp_checker_server.model.Temperature;
import com.example.arduino_temp_checker_server.repositories.TemperatureRepository;

@Service
public class TemperatureService {

    private TemperatureRepository temperatureRepository;

    public TemperatureService(TemperatureRepository temperatureRepository) {
        this.temperatureRepository = temperatureRepository;
    }

    public ResponseEntity<Temperature> addTemp(Temperature temp) {
        try {
            temperatureRepository.insert(temp);

            return ResponseEntity.ok(temp);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}

package com.example.arduino_temp_checker_server.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.example.arduino_temp_checker_server.dto.TempDTO;
import com.example.arduino_temp_checker_server.model.Temperature;
import com.example.arduino_temp_checker_server.service.TemperatureService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class WebSocketController {

    private SimpMessagingTemplate messagingTemplate;
    private TemperatureService temperatureService;

    public WebSocketController(SimpMessagingTemplate messagingTemplate, TemperatureService temperatureService) {
        this.messagingTemplate = messagingTemplate;
        this.temperatureService = temperatureService;
    }

    @PostMapping("/temp")
    public ResponseEntity<Temperature> postTemp(@RequestBody Temperature temp) {
        temp.setDate(LocalDate.now());

        messagingTemplate.convertAndSend("/topic/temp", temp);

        return temperatureService.addTemp(temp);
    }

    @GetMapping("/temps")
    public ResponseEntity<List<Temperature>> getAllTemps() {
        return temperatureService.getAllTemps();
    }

    @GetMapping("/temp/average")
    public String getAverageTemp() {
        // Hämta medeltemp senaste veckan
        return new String();
    }

    @GetMapping("/temp/minmax")
    public ResponseEntity<List<TempDTO>> getMinMaxTemp() {

        return temperatureService.getMinMaxTemp();
    }

}

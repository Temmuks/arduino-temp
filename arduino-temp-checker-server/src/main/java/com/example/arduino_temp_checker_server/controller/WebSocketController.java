package com.example.arduino_temp_checker_server.controller;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.example.arduino_temp_checker_server.model.Temperature;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class WebSocketController {

    @MessageMapping("/temp")
    @SendTo("/getTemp")
    public ResponseEntity<Temperature> getTemp(Temperature temp) {
        temp.setDate(LocalDate.now());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/post/temp")
    public Temperature postTemp(@RequestBody Temperature temp) {
        temp.setDate(LocalDate.now());
        return temp;
    }

}

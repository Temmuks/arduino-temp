package com.example.arduino_temp_checker_server.controller;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.example.arduino_temp_checker_server.model.Temperature;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketController {

    private SimpMessagingTemplate messagingTemplate;

    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping("/temp")
    public Temperature postTemp(@RequestBody Temperature temp) {
        temp.setDate(LocalDate.now());
        messagingTemplate.convertAndSend("/topic/temp", temp);
        return temp;
    }

}

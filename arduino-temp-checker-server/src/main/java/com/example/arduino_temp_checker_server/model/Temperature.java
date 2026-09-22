package com.example.arduino_temp_checker_server.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "temperatures")
public class Temperature {
    @Id
    String id;
    int temp;
    LocalDate date;

    public Temperature(String id, int temp, LocalDate date) {
        this.id = id;
        this.temp = temp;
        this.date = date;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}

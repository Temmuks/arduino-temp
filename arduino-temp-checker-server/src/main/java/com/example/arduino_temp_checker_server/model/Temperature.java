package com.example.arduino_temp_checker_server.model;

import java.time.LocalDate;

public class Temperature {
    int temp;
    LocalDate date;

    public Temperature(int temp, LocalDate date) {
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

}

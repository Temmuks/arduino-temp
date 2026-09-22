package com.example.arduino_temp_checker_server.dto;

import java.time.LocalDate;

public class TempDTO {

    LocalDate date;
    int min;
    int max;

    public TempDTO() {
    }

    public TempDTO(LocalDate date, int min, int max) {
        this.date = date;
        this.min = min;
        this.max = max;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

}

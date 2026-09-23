package com.example.arduino_temp_checker_server.service;

import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IntSummaryStatistics;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.arduino_temp_checker_server.dto.TempDTO;
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

    public ResponseEntity<List> getAllTemps() {
        try {
            List<Temperature> list = temperatureRepository.findAll();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }

    // https://www.geeksforgeeks.org/java/java-util-intsummarystatistics-class-with-examples/
    // https://www.baeldung.com/java-groupingby-collector
    public ResponseEntity<List> getMinMaxTemp() {

        try {
            List<Temperature> tempList = temperatureRepository.findAll();

            Map<LocalDate, IntSummaryStatistics> result = tempList.stream()
                    .collect(Collectors.groupingBy(Temperature::getDate,
                            Collectors.summarizingInt(Temperature::getTemp)));

            List<TempDTO> dtoList = new ArrayList<>();

            LocalDate startDate = LocalDate.now().minusDays(7);
            LocalDate endDate = LocalDate.now().plusDays(1);

            result.forEach((date, stats) -> {
                if (date.isAfter(startDate) && date.isBefore(endDate)) {
                    TempDTO temp = new TempDTO();

                    temp.setDate(date);
                    temp.setMin(stats.getMin());
                    temp.setMax(stats.getMax());
                    temp.setAverage(stats.getAverage());

                    dtoList.add(temp);
                }
            });

            // https://www.baeldung.com/java-reverse-arraylist
            Collections.reverse(dtoList);

            return ResponseEntity.ok(dtoList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }
}

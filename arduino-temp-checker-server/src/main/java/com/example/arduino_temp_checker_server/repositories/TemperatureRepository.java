package com.example.arduino_temp_checker_server.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.arduino_temp_checker_server.model.Temperature;

@Repository
public interface TemperatureRepository extends MongoRepository<Temperature, String> {

}
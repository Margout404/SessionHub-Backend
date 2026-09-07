package com.GA.gymApp.trainer.service;

import com.GA.gymApp.trainer.dto.TrainerResponseDTO;
import com.GA.gymApp.trainer.model.Trainer;
import com.GA.gymApp.trainer.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerService {

    @Autowired
    TrainerRepository repository;

    public List<TrainerResponseDTO> getAllTrainers(){

        List<Trainer> allTrainers= repository.findAll();

        return allTrainers.stream().map(trainer ->
                new TrainerResponseDTO(trainer.getId(), trainer.getFirstName(),trainer.getLastName())).toList();
    }
}

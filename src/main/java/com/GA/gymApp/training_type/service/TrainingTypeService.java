package com.GA.gymApp.training_type.service;

import com.GA.gymApp.trainer.dto.TrainerResponseDTO;
import com.GA.gymApp.training_type.dto.TrainingTypeResponseDTO;
import com.GA.gymApp.training_type.model.TrainingType;
import com.GA.gymApp.training_type.repository.TrainingTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingTypeService {

    @Autowired
    TrainingTypeRepository repository;


    public List<TrainingTypeResponseDTO> getAllTrainingTypes(){
        List<TrainingType> trainingTypes= repository.findAll();

        return trainingTypes.stream().map(trainingType ->
                new TrainingTypeResponseDTO(trainingType.getId(),
                        trainingType.getName(),
                        trainingType.getDescription(),
                        trainingType.getDuration())).toList();
    }
}

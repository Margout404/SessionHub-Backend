package com.GA.gymApp.training_type.controller;

import com.GA.gymApp.training_type.dto.TrainingTypeResponseDTO;
import com.GA.gymApp.training_type.model.TrainingType;
import com.GA.gymApp.training_type.service.TrainingTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/training-type")
public class TrainingTypeController {

    @Autowired
    TrainingTypeService service;


    @GetMapping("/get-all-types")
    public ResponseEntity<List<TrainingTypeResponseDTO>> getAllTypes(){
        return ResponseEntity.ok(service.getAllTrainingTypes());
    }
}

package com.GA.gymApp.trainer.controller;

import com.GA.gymApp.trainer.dto.TrainerResponseDTO;
import com.GA.gymApp.trainer.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/trainers")

public class TrainerController {

    @Autowired
    TrainerService service;

    @GetMapping("/all-trainers")
    public ResponseEntity<List<TrainerResponseDTO>> getAllTrainers(){
        return ResponseEntity.ok(service.getAllTrainers());
    }
}

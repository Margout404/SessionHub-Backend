package com.GA.gymApp.training_room.controller;

import com.GA.gymApp.training_room.dto.TrainingRoomResponseDTO;
import com.GA.gymApp.training_room.service.TrainingRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/training-room")
public class TrainingRoomController {


    @Autowired
    TrainingRoomService service;

    @GetMapping("all-rooms")
    public ResponseEntity<List<TrainingRoomResponseDTO>> getAllRooms(){
        return ResponseEntity.ok(service.getAllRooms());
    }
}

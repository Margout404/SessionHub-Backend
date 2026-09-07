package com.GA.gymApp.training_room.service;

import com.GA.gymApp.trainer.dto.TrainerResponseDTO;
import com.GA.gymApp.training_room.dto.TrainingRoomResponseDTO;
import com.GA.gymApp.training_room.model.TrainingRoom;
import com.GA.gymApp.training_room.repository.TrainingRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingRoomService {

    @Autowired
    TrainingRoomRepository repository;

    public List<TrainingRoomResponseDTO> getAllRooms(){

        List<TrainingRoom> rooms= repository.findAll();

        return rooms.stream().map(room->
                new TrainingRoomResponseDTO(
                        room.getId(),
                        room.getName(),
                        room.getDetails(),
                        room.getCapacity())).toList();
    }
}

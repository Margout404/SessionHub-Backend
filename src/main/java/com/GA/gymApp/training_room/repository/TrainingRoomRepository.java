package com.GA.gymApp.training_room.repository;

import com.GA.gymApp.training_room.model.TrainingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingRoomRepository extends JpaRepository<TrainingRoom,Long> {


}

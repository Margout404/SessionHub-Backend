package com.GA.gymApp.training_type.repository;

import com.GA.gymApp.training_type.model.TrainingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingTypeRepository extends JpaRepository<TrainingType,Long> {
}

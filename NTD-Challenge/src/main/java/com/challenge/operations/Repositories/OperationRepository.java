package com.challenge.operations.Repositories;

import com.challenge.operations.Entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, Long> {
    Operation findByType(String type);
}

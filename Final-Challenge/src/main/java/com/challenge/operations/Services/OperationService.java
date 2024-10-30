package com.challenge.operations.Services;

import java.util.List;
import java.util.Optional;

import com.challenge.operations.Entities.Operation;

public interface OperationService {
    List<Operation> findAll();
    Optional<Operation> findById(Long id);
    Operation save(Operation record);
    Optional<Operation> update(Operation record, Long id);
}

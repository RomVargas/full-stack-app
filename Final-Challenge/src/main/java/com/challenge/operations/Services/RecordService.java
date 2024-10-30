package com.challenge.operations.Services;

import java.util.List;
import java.util.Optional;

import com.challenge.operations.Entities.OperationRecord;

public interface RecordService {
    List<OperationRecord> findAll();
    Optional<OperationRecord> findById(Long id);
    OperationRecord save(OperationRecord record);
    //Optional<OperationRecord> update(OperationRecord record, Long id);
    //void remove(Long id); 
}

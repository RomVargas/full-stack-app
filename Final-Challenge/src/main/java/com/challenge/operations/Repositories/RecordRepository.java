package com.challenge.operations.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.challenge.operations.Entities.OperationRecord;

public interface RecordRepository extends JpaRepository<OperationRecord, Long> {
    //Optional<OperationRecord> findById(Long user_id);

    //void remove(Long id);

    //Optional<OperationRecord> update(OperationRecord record, Long id);
}

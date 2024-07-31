package com.challenge.operations.Repositories;

import java.util.Optional;

import org.hibernate.query.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.challenge.operations.Entities.OperationRecord;

public interface RecordRepository extends JpaRepository<OperationRecord, Long> {
    Optional<OperationRecord> findById(Long user_id);

    //void remove(Long id);

    //Optional<OperationRecord> update(OperationRecord record, Long id);
}

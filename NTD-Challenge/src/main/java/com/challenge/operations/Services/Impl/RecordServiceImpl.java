package com.challenge.operations.Services.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.operations.Entities.OperationRecord;
import com.challenge.operations.Repositories.RecordRepository;
import com.challenge.operations.Services.RecordService;

@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    RecordRepository repository;

    @Override
    public List<OperationRecord> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<OperationRecord> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public OperationRecord save(OperationRecord record) {
        return repository.save(record);
    }

    //@Override
    //public Optional<OperationRecord> update(OperationRecord record, Long id) {
    //    return repository.update(record, id);
    //}

   /* @Override
    public void remove(Long id) {
        repository.remove(id);
    } */
    
}

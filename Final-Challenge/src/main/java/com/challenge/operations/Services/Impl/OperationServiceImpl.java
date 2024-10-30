package com.challenge.operations.Services.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.operations.Entities.Operation;
import com.challenge.operations.Repositories.OperationRepository;
import com.challenge.operations.Services.OperationService;

@Service
public class OperationServiceImpl implements OperationService{

    @Autowired
    OperationRepository repository;

    @Override
    public List<Operation> findAll() {
       return repository.findAll();
    }

    @Override
    public Optional<Operation> findById(Long id) {
        Optional<Operation> o = repository.findById(id);
        Operation operation = null;
        if(o.isPresent()){
            operation = o.get();
        }else {
            throw new Error("no records found it with id : " + id);
        }
        return Optional.ofNullable(operation);
    }

    @Override
    public Operation save(Operation record) {
        return repository.save(record);
    }

    @Override
    public Optional<Operation> update(Operation record, Long id) {
        Optional<Operation> o = repository.findById(id);
        if(o.isPresent()){
            repository.save(record);
        } else {
            throw new Error("no record found with id : " + id);
        }
        return Optional.ofNullable(record);
    }
    
}

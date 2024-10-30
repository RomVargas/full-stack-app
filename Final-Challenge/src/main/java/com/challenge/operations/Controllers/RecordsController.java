package com.challenge.operations.Controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.operations.Entities.OperationRecord;
import com.challenge.operations.Services.RecordService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/records")
@CrossOrigin(originPatterns = "*")
public class RecordsController {
   
    @Autowired
    private RecordService service;

    @GetMapping
    public List<OperationRecord> list() {
        return service.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        Optional<OperationRecord> recordOptionl = service.findById(id);

        if (recordOptionl.isPresent()) {
            return ResponseEntity.ok(recordOptionl.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody  OperationRecord record, BindingResult result) {
        if(result.hasErrors()){
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(record));
    }

    /*@PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody OperationRecord record, BindingResult result, @PathVariable Long id) {
        if(result.hasErrors()){
            return validation(result);
        }
        Optional<OperationRecord> o = service.update(record, id);
        
        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id) {
        Optional<OperationRecord> o = service.findById(id);

        if (o.isPresent()) {
            service.remove(id);
            return ResponseEntity.noContent().build(); // 204
        }
        return ResponseEntity.notFound().build();
    }*/
    
    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    } 
}

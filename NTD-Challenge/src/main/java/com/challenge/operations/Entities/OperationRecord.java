package com.challenge.operations.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE record SET deleted = true WHERE id = ?")
public class OperationRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // Relación muchos a uno
    @JoinColumn(name = "operation_id")
    private Operation operation; // Debe ser una entidad Operation
    
    @ManyToOne // Relación muchos a uno
    @JoinColumn(name = "user_id")
    private User user; // Debe ser una entidad User

    /*@ManyToOne(targetEntity =  Operation.class )
    @JoinColumn(name = "operation_id")
    private Long operationId;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name="user_id")
    private Long userId;*/

    @NotBlank
    private Double amount;
    
    @NotBlank
    private Double userBalance;
    
    @NotBlank
    private String operationResponse;
    
    @NotBlank
    private LocalDateTime date;

}

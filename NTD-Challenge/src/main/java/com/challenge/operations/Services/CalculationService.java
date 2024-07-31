package com.challenge.operations.Services;

import com.challenge.operations.Entities.Operation;
import com.challenge.operations.Entities.OperationRecord;
import com.challenge.operations.Entities.User;
import com.challenge.operations.Repositories.OperationRepository;
import com.challenge.operations.Repositories.RecordRepository;
import com.challenge.operations.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CalculationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OperationRepository operationRepository;
    @Autowired
    private RecordRepository recordRepository;
    //@Autowired
    //private RestTemplate restTemplate;

    public String performOperation(Long userId, String operationType, double... operands) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new RuntimeException("User not found");
        }

        User user = userOptional.get();
        Operation operation = operationRepository.findByType(operationType);
        //if (user.getBalance() < operation.getCost()) {
          //  return "Insufficient balance";
        //}

        String result;
        switch (operationType) {
            case "addition":
                result = String.valueOf(operands[0] + operands[1]);
                break;
            case "subtraction":
                result = String.valueOf(operands[0] - operands[1]);
                break;
            case "multiplication":
                result = String.valueOf(operands[0] * operands[1]);
                break;
            case "division":
                result = String.valueOf(operands[0] / operands[1]);
                break;
            case "square_root":
                result = String.valueOf(Math.sqrt(operands[0]));
                break;
            case "random_string":
                result = fetchRandomString();
                break;
            default:
                throw new RuntimeException("Invalid operation type");
        }

        //user.setBalance(user.getBalance() - operation.getCost());
        userRepository.save(user);

        OperationRecord record = new OperationRecord() {
            @Override
            public boolean equals(Object obj) {
                return false;
            }

            @Override
            public int hashCode() {
                return 0;
            }

            @Override
            public String toString() {
                return null;
            }
        };
        record.setUserId(userId);
        record.setOperationId(operation.getId());
        record.setAmount(operation.getCost());
        //record.setUserBalance(user.getBalance());
        record.setOperationResponse(result);
        record.setDate(LocalDateTime.now());
        recordRepository.save(record);

        return result;
    }

    private String fetchRandomString() {
        String url = "https://www.random.org/clients"; // URL del servicio de terceros
        //return restTemplate.getForObject(url, String.class);
        return url;
    }
}

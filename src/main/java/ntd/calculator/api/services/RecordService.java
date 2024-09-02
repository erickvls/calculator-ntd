package ntd.calculator.api.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ntd.calculator.api.models.account.Account;
import ntd.calculator.api.models.operation.Operation;
import ntd.calculator.api.models.record.Record;
import ntd.calculator.api.models.responses.RecordResponse;
import ntd.calculator.api.models.user.User;
import ntd.calculator.api.repositories.RecordRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecordService extends ResponseServiceBase{
    private final RecordRepository recordRepository;

    @Transactional
    public Record createRecord(User user, Account account, Operation operation, BigDecimal cost, String result) {
        var recordResult = Record.builder()
                .user(user)
                .amount(cost)
                .operation(operation)
                .userBalance(account.getBalance())
                .operationResponse(result)
                .date(new Date())
                .build();

        return recordRepository.save(recordResult);
    }

    public List<RecordResponse> findRecordsByUser(User user){
        var result = recordRepository.findRecordByUser(user);
        return Collections.emptyList();
    }
}

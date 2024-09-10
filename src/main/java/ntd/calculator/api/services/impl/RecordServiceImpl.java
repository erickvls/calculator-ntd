package ntd.calculator.api.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ntd.calculator.api.exceptions.RecordNotFoundException;
import ntd.calculator.api.models.account.Account;
import ntd.calculator.api.models.operation.Operation;
import ntd.calculator.api.models.record.Record;
import ntd.calculator.api.models.responses.RecordResponse;
import ntd.calculator.api.models.user.User;
import ntd.calculator.api.repositories.RecordRepository;
import ntd.calculator.api.services.RecordService;
import ntd.calculator.api.services.ResponseServiceBase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class RecordServiceImpl extends ResponseServiceBase implements RecordService {
    private final RecordRepository recordRepository;

    @Transactional
    public Record createRecord(User user, Account account, Operation operation, BigDecimal cost, String result) {
        var recordResult = Record.builder()
                .user(user)
                .amount(cost)
                .operation(operation)
                .userBalance(account.getBalance())
                .operationResponse(result)
                .deleted(false)
                .date(new Date())
                .build();

        return recordRepository.save(recordResult);
    }

    public Page<RecordResponse> findRecordsByUser(User user, int page, int size, String sort) {
        var sortOrder = parseSort(sort);
        var pageable = PageRequest.of(page, size, sortOrder);
        var recordsPage = recordRepository.findRecordByUserAndDeletedIsFalse(user, pageable);
        return recordsPage.map(this::createRecordResponse);
    }

    @Transactional
    public void deleteRecord(User user, Long recordId) {
        var userRecord = recordRepository.findByIdAndUser(recordId, user).orElseThrow(() -> new RecordNotFoundException("Record not found"));
        userRecord.setDeleted(true);
        recordRepository.save(userRecord);
    }


    private Sort parseSort(String sort) {
        String[] sortParams = sort.split(",");
        if (sortParams.length == 2) {
            return Sort.by(Sort.Direction.fromString(sortParams[1]), sortParams[0]);
        }
        return Sort.unsorted();
    }
}

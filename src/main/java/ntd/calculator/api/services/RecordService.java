package ntd.calculator.api.services;

import ntd.calculator.api.models.account.Account;
import ntd.calculator.api.models.operation.Operation;
import ntd.calculator.api.models.record.Record;
import ntd.calculator.api.models.responses.RecordResponse;
import ntd.calculator.api.models.user.User;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

public interface RecordService {
    Record createRecord(User user, Account account, Operation operation, BigDecimal cost, String result);

    Page<RecordResponse> findRecordsByUser(User user, int page, int size, String sort);

    void deleteRecord(User user, Long recordId);
}

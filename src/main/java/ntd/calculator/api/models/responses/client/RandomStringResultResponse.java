package ntd.calculator.api.models.responses.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class RandomStringResultResponse {
    private ResultRandomString random = new ResultRandomString();
}

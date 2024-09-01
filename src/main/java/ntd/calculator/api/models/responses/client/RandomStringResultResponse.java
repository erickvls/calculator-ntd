package ntd.calculator.api.models.responses.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class RandomStringResultResponse {
    private ResultRandomString random;
}

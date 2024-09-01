package ntd.calculator.api.models.responses.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class RandomStringGeneratorResponse {
    private RandomStringResultResponse result = new RandomStringResultResponse();
}

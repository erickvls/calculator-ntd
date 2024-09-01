package ntd.calculator.api.models.requests.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class ParamsStringRequest {
    private String apiKey;
    private Integer n;
    private Integer length;
    private String characters;
    private boolean replacement;
}

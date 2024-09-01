package ntd.calculator.api.models.requests.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class RandomStringGeneratorRequest {
    private String jsonrpc;
    private String method;
    private ParamsStringRequest params;
    private Integer id;
}

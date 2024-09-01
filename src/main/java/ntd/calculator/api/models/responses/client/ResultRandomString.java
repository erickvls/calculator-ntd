package ntd.calculator.api.models.responses.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ResultRandomString {
    private List<String> data = new ArrayList<>();
}

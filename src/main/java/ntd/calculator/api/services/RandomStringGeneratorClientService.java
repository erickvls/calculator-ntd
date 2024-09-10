package ntd.calculator.api.services;

import java.io.IOException;

public interface RandomStringGeneratorClientService {
    String generateString() throws IOException, InterruptedException;
}

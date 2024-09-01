package ntd.calculator.api.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import ntd.calculator.api.config.ConfigProperties;
import ntd.calculator.api.models.requests.client.ParamsStringRequest;
import ntd.calculator.api.models.requests.client.RandomStringGeneratorRequest;
import ntd.calculator.api.models.responses.client.RandomStringGeneratorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static ntd.calculator.api.utility.RandomStringConstants.*;

@Service
@RequiredArgsConstructor
public class RandomStringGeneratorClient {

    private final ConfigProperties configProperties;
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient = HttpClient.newBuilder().build();

    public String generateString() throws IOException, InterruptedException {
        var uri = buildUri();
        var requestObject = createRequest();
        var json = objectMapper.writeValueAsString(requestObject);
        var request = createHttpRequest(json, uri);
        var response = sendRequest(request);
        return parseResponse(response);
    }

    private HttpRequest createHttpRequest(String json, URI uri) {
        var body = HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8);
        return HttpRequest.newBuilder(uri)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .POST(body)
                .build();
    }

    private String sendRequest(HttpRequest httpRequest) throws IOException, InterruptedException {
        var httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return httpResponse.body();
    }

    private String parseResponse(String body) {
        try {
            var json = objectMapper.readValue(body, RandomStringGeneratorResponse.class);
            return json.getRandomStringResult().getRandom().getData().stream()
                    .findFirst()
                    .orElse("");
        } catch (JsonProcessingException e) {
            return body;
        }
    }

    private RandomStringGeneratorRequest createRequest() {
        return RandomStringGeneratorRequest.builder()
                .id(generateRandomId())
                .method(METHOD_NAME)
                .params(createParams())
                .build();
    }

    private URI buildUri() {
        var host = configProperties.getRandomString().getClientUri();
        return URI.create(host);
    }

    private ParamsStringRequest createParams() {
        return ParamsStringRequest.builder()
                .apiKey(configProperties.getRandomString().getApiKey())
                .n(1)
                .length(STRING_LENGTH)
                .characters(CHARACTERS)
                .replacement(true)
                .build();
    }

    private int generateRandomId() {
        return new Random().nextInt((MAX_NUMBER - MIN_NUMBER) + 1) + MIN_NUMBER;
    }
}

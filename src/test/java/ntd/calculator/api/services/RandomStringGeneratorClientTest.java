package ntd.calculator.api.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ntd.calculator.api.config.ConfigProperties;
import ntd.calculator.api.config.properties.RandomString;
import ntd.calculator.api.exceptions.ParseResponseException;
import ntd.calculator.api.models.requests.client.RandomStringGeneratorRequest;
import ntd.calculator.api.models.responses.client.RandomStringGeneratorResponse;
import ntd.calculator.api.models.responses.client.RandomStringResultResponse;
import ntd.calculator.api.models.responses.client.ResultRandomString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RandomStringGeneratorClientTest {

    @Mock
    private ConfigProperties configProperties;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private HttpClient httpClient;

    @Mock
    private HttpResponse<String> httpResponse;

    @InjectMocks
    private RandomStringGeneratorClient randomStringGeneratorClient;


    @Test
    void shouldReturnStringSuccess() throws IOException, InterruptedException {
        var randomString = createRandomString();
        var response = createSuccessResponse();

        when(configProperties.getRandomString()).thenReturn(randomString);
        when(objectMapper.writeValueAsString(any(RandomStringGeneratorRequest.class)))
                .thenReturn("{\"jsonrpc\":\"2.0\"}");
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(httpResponse);
        when(httpResponse.body()).thenReturn("{\"result\":{\"random\":{\"data\":[\"RANDOM_STRING\"]}}}");
        when(objectMapper.readValue(anyString(), eq(RandomStringGeneratorResponse.class)))
                .thenReturn(response);

        var result = randomStringGeneratorClient.generateString();

        assertEquals("RANDOM_STRING", result);
        verifyInteractions();
    }

    @Test
    void shouldThrowIOException() throws IOException, InterruptedException {
        var randomString = createRandomString();

        when(configProperties.getRandomString()).thenReturn(randomString);
        when(objectMapper.writeValueAsString(any())).thenReturn("{}");
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(new IOException("Client IO Error"));

        var exception = assertThrows(IOException.class, () -> randomStringGeneratorClient.generateString());
        assertEquals("Client IO Error", exception.getMessage());
    }

    @Test
    void shouldThrowInterruptedException() throws IOException, InterruptedException {
        var randomString = createRandomString();

        when(configProperties.getRandomString()).thenReturn(randomString);
        when(objectMapper.writeValueAsString(any())).thenReturn("{}");
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(new InterruptedException("Client Interrupted Error"));

        var exception = assertThrows(InterruptedException.class, () -> randomStringGeneratorClient.generateString());
        assertEquals("Client Interrupted Error", exception.getMessage());
    }

    @Test
    void shouldThrowParseResponseExceptionWhenStringIsNotParsed() throws IOException, InterruptedException {
        var randomString = createRandomString();

        when(configProperties.getRandomString()).thenReturn(randomString);
        when(objectMapper.writeValueAsString(any())).thenReturn("{}");
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(httpResponse);
        when(httpResponse.body()).thenReturn("{\"invalid_json\": \"data\"}");
        when(objectMapper.readValue(anyString(), eq(RandomStringGeneratorResponse.class)))
                .thenThrow(new JsonProcessingException("JSON Parsing Error") {
                });

        var exception = assertThrows(ParseResponseException.class,
                () -> randomStringGeneratorClient.generateString());
        assertEquals("JSON Parsing Error", exception.getMessage());
    }

    private RandomString createRandomString() {
        var randomString = new RandomString();
        randomString.setClientUri("https://api.example.com");
        randomString.setApiKey("fake-api-key");
        return randomString;
    }

    private RandomStringGeneratorResponse createSuccessResponse() {
        var resultRandomString = new ResultRandomString();
        resultRandomString.setData(List.of("RANDOM_STRING"));
        var randomStringResultResponse = new RandomStringResultResponse(resultRandomString);
        return new RandomStringGeneratorResponse(randomStringResultResponse);
    }

    private void verifyInteractions() throws IOException, InterruptedException {
        verify(objectMapper).writeValueAsString(any(RandomStringGeneratorRequest.class));
        verify(httpClient).send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
        verify(objectMapper).readValue(anyString(), eq(RandomStringGeneratorResponse.class));
    }
}
package ru.practicum.client.web;


import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

public class WebBaseClient {
    protected final RestTemplate rest;

    public WebBaseClient(RestTemplate rest) {
        this.rest = rest;
    }

    protected <T, V> ResponseEntity<V> post(T body, V responseType) {
        return makeAndSendRequest(HttpMethod.POST, "/hit", null, body, responseType);
    }

    protected <V> ResponseEntity<V> get(Map<String, Object> parameters, V responseType) {
        return makeAndSendRequest(
                HttpMethod.GET, "/stats?start={start}&end={end}&uris={uris}&uniq={unique}",
                parameters, null, responseType);
    }

    private <T, V> ResponseEntity<V> makeAndSendRequest(HttpMethod method,
                                                        String path,
                                                        @Nullable Map<String, Object> parameters,
                                                        @Nullable T body,
                                                        V responseType) {
        HttpEntity<T> requestEntity = new HttpEntity<>(body, defaultHeaders());
        Class tClass = responseType.getClass();

        ResponseEntity<V> statsServerResponse;
        try {
            if (parameters != null) {
                statsServerResponse = rest.exchange(path, method, requestEntity, tClass, parameters);
            } else {
                statsServerResponse = rest.exchange(path, method, requestEntity, tClass);
            }
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode()).body((V) e.getResponseBodyAsByteArray());
        }
        return prepareGatewayResponse(statsServerResponse);
    }

    private HttpHeaders defaultHeaders() {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        return headers;
    }

    private static <T> ResponseEntity<T> prepareGatewayResponse(ResponseEntity<T> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        }

        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());

        if (response.hasBody()) {
            return responseBuilder.body(response.getBody());
        }

        return responseBuilder.build();
    }
}
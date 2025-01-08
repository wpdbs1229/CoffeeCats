package org.hidevelop.coffeecats.config;

import feign.RequestInterceptor;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.hidevelop.coffeecats.exception.CustomException;
import org.hidevelop.coffeecats.exception.error.impl.GoogleCommunicationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;


public class GooglePlaceFeignConfig {

    private static final Logger log = LoggerFactory.getLogger(GooglePlaceFeignConfig.class);
    @Value("${google.api.key}")
    private String googleApiKey;

    @Bean
    public RequestInterceptor googleApiKeyRequestInterceptor() {
        return template -> template.header("X-Goog-Api-Key", googleApiKey);
    }

    @Bean
    public ErrorDecoder googleErrorDecoder() {
        return this::decode;
    }

    private Exception decode(String methodKey ,Response response) {
        String responseBody = extractResponseBody(response);
        log.error("Error in method: {}", methodKey);
        log.error("Response body: {}", responseBody);
        return new CustomException(GoogleCommunicationError.EXTERNAL_SERVER_FAILED);
    }

    private String extractResponseBody(Response response) {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(response.body().asInputStream(), StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e){
            throw new RuntimeException("변환 실패");
        }
    }

}

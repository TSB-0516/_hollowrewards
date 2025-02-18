package com.example.demo;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.AffiliateStats;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/affiliate")
@CrossOrigin(origins = "*")
public class ProxyController {
    
    @Value("${affiliate.api.url}")
    private String apiUrl;

    @Value("${affiliate.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public ProxyController(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/stats")
    public ResponseEntity<?> getCreatorStats(@RequestBody(required = false) Map<String, String> requestParams) {
        try {
            // Create request body
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("apikey", apiKey);
            
            // If dates are provided in the request, use them
            if (requestParams != null) {
                if (requestParams.containsKey("from")) {
                    requestBody.put("from", requestParams.get("from"));
                }
                if (requestParams.containsKey("to")) {
                    requestBody.put("to", requestParams.get("to"));
                }
            }

            // If dates are not provided, use defaults
            if (!requestBody.containsKey("from")) {
                requestBody.put("from", LocalDate.now().minusDays(7).toString());
            }
            if (!requestBody.containsKey("to")) {
                requestBody.put("to", LocalDate.now().toString());
            }

            // Set headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Accept", "application/json");

            // Create request entity
            HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

            // Make the request
            String fullUrl = apiUrl + "/affiliate/creator/get-stats";
            System.out.println("Making request to: " + fullUrl);
            System.out.println("Request body: " + objectMapper.writeValueAsString(requestBody));

            ResponseEntity<String> response = restTemplate.exchange(
                fullUrl,
                HttpMethod.POST,
                entity,
                String.class
            );

            System.out.println("Response status: " + response.getStatusCode());
            System.out.println("Response body: " + response.getBody());

            return ResponseEntity.ok(response.getBody());

        } catch (IllegalArgumentException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", true);
            errorResponse.put("msg", e.getMessage());
            errorResponse.put("retryable", false);
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", true);
            errorResponse.put("msg", e.getMessage());
            errorResponse.put("retryable", true);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                               .body(errorResponse);
        }
    }
} 
package com.example.user_service.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.user_service.dto.AzurePromptRequest;
import com.example.user_service.dto.AzurePromptResponse;

@Component
public class AzurePromptFlowClient {
@Value("${azure.endpoint}")
    private String azureEndpoint;
    
    @Value("${azure.apiKey}")
    private String apiKey;

    public List<String> analyzeCode(List<String> fileContents) throws Exception {
        String prompt = "Please check if the code follows all best practices. " +
                        "Is this the best method to solve this LLD problem? " +
                        "What improvements do you suggest? " +
                        "Are there security vulnerabilities? " +
                        "Does the code follow SOLID principles? " +
                        "Are there performance optimizations that can be made?";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + apiKey);

        AzurePromptRequest request = new AzurePromptRequest(prompt, fileContents);
        HttpEntity<AzurePromptRequest> requestEntity = new HttpEntity<>(request, headers);
        
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<AzurePromptResponse> response = restTemplate.exchange(azureEndpoint, HttpMethod.POST, requestEntity, AzurePromptResponse.class);
        
        return response.getBody().getResponses();
    }
}
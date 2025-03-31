package com.example.user_service.dto;

import java.util.List;

public class AzurePromptRequest {
    private String prompt;
    private List<String> files;
    
    public AzurePromptRequest(String prompt, List<String> files) {
        this.prompt = prompt;
        this.files = files;
    }

    private String getPrompts() {
        return prompt;
    }
}

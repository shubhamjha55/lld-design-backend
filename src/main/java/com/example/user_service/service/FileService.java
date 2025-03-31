package com.example.user_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.user_service.config.AzurePromptFlowClient;
import com.example.user_service.util.FileUtil;

@Service
public class FileService {
    
    @Autowired
    private AzurePromptFlowClient azureClient;
    
    public List<String> analyzeFiles(MultipartFile[] files) throws Exception {
        List<String> fileContents = FileUtil.convertFilesToString(files);
        return azureClient.analyzeCode(fileContents);
    }
}
package com.example.user_service.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
    public static List<String> convertFilesToString(MultipartFile[] files) throws Exception {
        return Arrays.stream(files).map(file -> {
            try {
                return new String(file.getBytes());
            } catch (Exception e) {
                throw new RuntimeException("Error reading file: " + file.getOriginalFilename(), e);
            }
        }).collect(Collectors.toList());
    }
}

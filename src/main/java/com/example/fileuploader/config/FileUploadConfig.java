package com.example.fileuploader.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class FileUploadConfig implements CommandLineRunner {
    @Value("${uploaded-files.dirname}")
    private String dirName;

    //Creates the directory if it doesn't exist yet
    @Override
    public void run(String... args) throws Exception {
        Path uploadPath = Paths.get(dirName);

        if(!Files.exists(uploadPath)){
            Files.createDirectory(uploadPath);
        }
    }
}

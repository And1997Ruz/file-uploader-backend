package com.example.fileuploader.controller;

import com.example.fileuploader.dto.FileMetadataDto;
import com.example.fileuploader.service.FilesService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequestMapping(value = "/api")
@AllArgsConstructor
@CrossOrigin(value = "*")
public class FilesController {
    private final FilesService filesService;

    @PostMapping(value = "/images")
    public ResponseEntity<String> uploadFiles(@RequestParam("file") MultipartFile[] files) throws IOException {
        filesService.uploadFiles(files);
        return ResponseEntity.ok().body("Files have been uploaded successfully");
    }

    @GetMapping(value = "/images")
    public ResponseEntity<List<FileMetadataDto>> getFiles(){
        List<FileMetadataDto> files = filesService.getFiles();
        return ResponseEntity.ok().body(files);
    }

    @GetMapping(value = "/images/{filename}")
    public ResponseEntity<FileMetadataDto> getImage(@PathVariable("filename") String fileName) {
        return ResponseEntity.ok().body(filesService.getFile(fileName));
    }

    @GetMapping(value = "/static/{filename}",
            produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
    public byte[] getImage(@PathVariable("filename") String fileName, HttpServletResponse response) throws IOException {

        return filesService.getStaticFile(fileName);
    }
}

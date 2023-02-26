package com.example.fileuploader.service;

import com.example.fileuploader.dto.FileMetadataDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FilesService {

    void uploadFiles(MultipartFile[] files) throws IOException;
    FileMetadataDto getFile(String fileName);
    List<FileMetadataDto> getFiles();

    byte[] getStaticFile(String filename) throws IOException;
}

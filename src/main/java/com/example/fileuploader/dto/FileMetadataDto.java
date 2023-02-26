package com.example.fileuploader.dto;
import com.drew.metadata.Tag;
import lombok.Data;

import java.util.List;

@Data
public class FileMetadataDto {

    String filename;
    List<Tag> metadata;

    public FileMetadataDto(String filename, List<Tag> metadataDirectories){
        this.filename = filename;
        this.metadata = metadataDirectories;
    }
}
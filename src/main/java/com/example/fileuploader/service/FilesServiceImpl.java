package com.example.fileuploader.service;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.example.fileuploader.dto.FileMetadataDto;
import com.example.fileuploader.exception.NotFoundException;
import com.example.fileuploader.exception.UnsupportedFileFormatException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class FilesServiceImpl implements FilesService{

    @Value("${uploaded-files.dirname}")
    private String dirName;
    private final List<String> supportedFileFormats = List.of("image/jpeg", "image/png");

    @Override
    public void uploadFiles(MultipartFile[] files) throws IOException {
        this.checkSupportedFileFormats(files);

        for(MultipartFile file : files){
            file.transferTo(this.getUniquesFileName(file));
        }
    }

    @Override
    public List<FileMetadataDto> getFiles(){

        File[] files = new File(this.dirName).listFiles();

        List<FileMetadataDto> fileMetadataDtos = Stream.of(files)
                .filter(file -> !file.isDirectory())
                .map(this::getMetadata).toList();

        return fileMetadataDtos;
    }

    @Override
    public FileMetadataDto getFile(String fileName){
        try{
            File file = new File(this.dirName + "/" + fileName);
            return this.getMetadata(file);
        }catch(Exception ex){
            throw new NotFoundException(fileName);
        }
    }

    @Override
    public byte[] getStaticFile(String filename) throws IOException {
        return Files.readAllBytes(new File(this.dirName + "/" + filename).toPath());
    }

    private FileMetadataDto getMetadata(File file) {
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(file);
            List<Tag> tags = this.filterSpecificMetadata("EXIF", metadata);

            return new FileMetadataDto(file.toPath().getFileName().toString(), tags);
        } catch (ImageProcessingException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Tag> filterSpecificMetadata(String metadataType, Metadata rawMetadata){
        List<Tag> tags = new ArrayList<>();

        rawMetadata.getDirectories().forEach(directory -> {
            if(directory.toString().toLowerCase().contains(metadataType.toLowerCase())){
                tags.addAll(directory.getTags());
            }
        });

        return tags;
    }
    private void checkSupportedFileFormats(MultipartFile[] files){
        for(MultipartFile file : files){
            String contentType = file.getContentType();
            if(!this.supportedFileFormats.contains(contentType)){
                throw new UnsupportedFileFormatException(file.getOriginalFilename());
            }
        }
    }

    private Path getUniquesFileName(MultipartFile file){
        return Paths.get(this.dirName + "\\" + file.getOriginalFilename());
    }
}

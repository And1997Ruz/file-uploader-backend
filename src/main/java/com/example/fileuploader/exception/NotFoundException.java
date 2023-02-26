package com.example.fileuploader.exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String fileName){
        super(String.format("File with the name: %s not found", fileName));
    }
}
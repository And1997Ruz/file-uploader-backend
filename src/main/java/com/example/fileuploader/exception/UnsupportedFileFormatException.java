package com.example.fileuploader.exception;

public class UnsupportedFileFormatException extends RuntimeException{
    public UnsupportedFileFormatException(String fileName){
        super(String.format("Unsupported file format for: %s Provide [jpeg, jpg, png]", fileName));
    }
}

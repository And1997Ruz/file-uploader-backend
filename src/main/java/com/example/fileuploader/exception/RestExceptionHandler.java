package com.example.fileuploader.exception;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { UnsupportedFileFormatException.class, NotFoundException.class })
    public ResponseEntity<Object> handleUnsupportedFileFormatException(RuntimeException ex){
        Map<String, String> body = new HashMap<>();

        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now().toString());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { FileSizeLimitExceededException.class, SizeLimitExceededException.class })
    public ResponseEntity<Object> handleFileSizeLimitExceededException(RuntimeException ex){
        Map<String, String> body = new HashMap<>();

        body.put("message", "Max file size is 10mb for each file, max combined size is 20mb");
        body.put("cause", ex.getMessage());
        body.put("timestamp", LocalDateTime.now().toString());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}

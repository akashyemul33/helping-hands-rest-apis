package com.ayprojects.helpinghands.exceptions;

import com.ayprojects.helpinghands.models.Response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;

@ControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler(ClientSideException.class)
public ResponseEntity<Response<String>> clientSideExceptionResponse(ClientSideException ex) {
    return new ResponseEntity<Response<String>>(new Response<>(false,400,ex.getMessage(),new ArrayList<>()), HttpStatus.BAD_REQUEST);
}

    @ExceptionHandler(ServerSideException.class)
    public ResponseEntity<Response<String>> serverSideExceptionResponse(ServerSideException ex) {
        return new ResponseEntity<Response<String>>(new Response<>(false,500,ex.getMessage(),new ArrayList<>()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

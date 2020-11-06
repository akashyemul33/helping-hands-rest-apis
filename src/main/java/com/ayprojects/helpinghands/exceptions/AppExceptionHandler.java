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
    Response<String> response = new Response<String>();
    response.setStatus(false);
    response.setStatusCode(400);
    response.setMessage(ex.getMessage());
    response.setData(new ArrayList<>());
    // System.out.println(ex.getStackTrace());
    return new ResponseEntity<Response<String>>(response, HttpStatus.BAD_REQUEST);
}

    @ExceptionHandler(ServerSideException.class)
    public ResponseEntity<Response<String>> serverSideExceptionResponse(ServerSideException ex) {
        Response<String> response = new Response<String>();
        response.setStatus(false);
        response.setStatusCode(500);
        response.setMessage(ex.getMessage());
        response.setData(new ArrayList<>());
        return new ResponseEntity<Response<String>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

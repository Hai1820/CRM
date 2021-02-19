package com.myclass.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomReponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity<Object> handleProjectIdException(ProjectIdException ex , WebRequest request){
        ProjectIdExceptionReponse exceptionReponse = new ProjectIdExceptionReponse(ex.getMessage());
        return new ResponseEntity(exceptionReponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleProjectNotFoundException(ProjectNotFoundException ex , WebRequest request){
        ProjectNotFoundExceptionResponse exceptionReponse = new ProjectNotFoundExceptionResponse(ex.getMessage());
        return new ResponseEntity(exceptionReponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public final ResponseEntity<Object> handleUsernameAlreadyExists(UsernameAlreadyExistsException ex , WebRequest request){
        UsernameAlreadyExistsResponse exceptionReponse = new UsernameAlreadyExistsResponse(ex.getMessage());
        return new ResponseEntity(exceptionReponse, HttpStatus.BAD_REQUEST);
    }
}

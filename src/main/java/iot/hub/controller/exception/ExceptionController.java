package iot.hub.controller.exception;

import iot.hub.exception.DataAlreadyLikeThatException;
import iot.hub.exception.DiedDeviceException;
import iot.hub.exception.ResourceAlreadyExistException;
import iot.hub.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {
            ResourceAlreadyExistException.class,
            DataAlreadyLikeThatException.class
    })
    public ResponseEntity<Response> handlerResourceAlreadyExistException(Exception exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new Response(exception.getMessage()));
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<Response> handlerResourceNotFoundException(Exception exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(exception.getMessage()));
    }

    @ExceptionHandler(value = {
            ClassCastException.class,
            DiedDeviceException.class
    })
    public ResponseEntity<Response> handlerInvalidCommandException(Exception exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(exception.getMessage()));
    }

    @Data
    @AllArgsConstructor
    private static class Response {
        private String message;
    }

}

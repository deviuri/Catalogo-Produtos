package com.catalogo.Infra;


import com.catalogo.resouces.exceptions.EntityNotFoundException;
import com.catalogo.resouces.exceptions.StandardError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class TratadorDeErros{

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardError> error404(
            EntityNotFoundException e, HttpServletRequest http){

        StandardError error = new StandardError();
        error.setTimestamp(Instant.now());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setError("Não encontrado");
        error.setMessage(e.getMessage());
        error.setPath(http.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

}

package com.ShopEasy.config;

import com.ShopEasy.exception.EstoqueInsuficienteException;
import com.ShopEasy.exception.ExceptionModel;
import com.ShopEasy.exception.PedidoJaCanceladoException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptinHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionModel> handleException(EntityNotFoundException ex) {
        ExceptionModel exception = new ExceptionModel(
                LocalDateTime.now(),
                "Not found",
                404,
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleException(MethodArgumentNotValidException ex) {
        Map<String, String> exception = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                exception.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception);
    }

    @ExceptionHandler(EstoqueInsuficienteException.class)
    public ResponseEntity<Object> handleExceptionEstoqueInsuficiente(EstoqueInsuficienteException ex) {
        ExceptionModel exception = new ExceptionModel(
                LocalDateTime.now(),
                "Unprocessable Entity",
                422,
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(exception);
    }
    @ExceptionHandler(PedidoJaCanceladoException.class)
    public ResponseEntity<Object> handleExceptionPedidoJaCancelado(PedidoJaCanceladoException ex) {
        ExceptionModel exception = new ExceptionModel(
                LocalDateTime.now(),
                "Bad request",
                400,
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception);
    }
}

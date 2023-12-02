package com.digitalHouse.beerClub.exceptions;

import com.digitalHouse.beerClub.model.dto.ExceptionDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> notFoundException(Exception e) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> badRequestException(Exception e) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<?> forbiddenException(Exception e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

    /*@ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> notFoundExceptionUser(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }*/

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException e) {
        ExceptionDTO errors = new ExceptionDTO(e.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).toList());
        return new ResponseEntity<>(errors, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleValidationErrors(ConstraintViolationException e) {
        ExceptionDTO errors = new ExceptionDTO(e.getConstraintViolations()
                .stream().map(ConstraintViolation::getMessage).toList());
        return new ResponseEntity<>(errors, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityInactiveException.class)
    public ResponseEntity<?> entityInactiveException(EntityInactiveException e) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<?> insufficientBalanceException(InsufficientBalanceException e) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }

    @ExceptionHandler(UserActiveException.class)
    public ResponseEntity<?> userActiveException(UserActiveException e) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }

    @ExceptionHandler(CustomUserAlreadyExistsException.class)
    public ResponseEntity<?> customUserAlreadyExistsException(CustomUserAlreadyExistsException e) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<?> NoDataFoundException(NoDataFoundException e) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleTypeMismatch(MethodArgumentTypeMismatchException e) {
        Map<String, Object> map = new HashMap<>();
        String fieldName = e.getName();
        String expectedType = e.getRequiredType().getSimpleName();
        map.put("message", "Tipo de dato incorrecto para el campo '" + fieldName + "'. Se esperaba un valor de tipo " + expectedType + ".");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<Object> handleDateTimeParse(DateTimeParseException e) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", "Formato de fecha inválido. Por favor, use el formato correcto (por ejemplo, 'aaaa-mm-dd').");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }


}

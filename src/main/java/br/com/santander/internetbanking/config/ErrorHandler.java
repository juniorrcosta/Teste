package br.com.santander.internetbanking.config;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class ErrorHandler {
	
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationData>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<ValidationData> validationDataList = ex.getFieldErrors().stream()
                .map(ValidationData::new)
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(validationDataList);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    private record ValidationData(String campo, String mensagem) {
        public ValidationData(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }
    
    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<String> handlePropertyReferenceException(PropertyReferenceException ex) {
        String errorMessage = "Erro ao acessar propriedade: " + ex.getPropertyName() +". Verifique se os paramêtros estão corretos.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
    
    @ExceptionHandler(ResourceBusinessException.class)
    public ResponseEntity<String> handlePropertyReferenceException(ResourceBusinessException ex) {
        return ResponseEntity.status(ex.getHttpStatus()).body(ex.getMessage());
    }
    
    
}
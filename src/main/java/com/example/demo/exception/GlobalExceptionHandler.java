package com.example.demo.exception;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// Spring richiamerà questi metodi automaticamente quando le eccezioni risalgono dal service attraverso il controller
@ControllerAdvice        // Registra la classe per la gestione condivisa delle eccezioni dei controller
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidLetterException.class) // Indica quale eccezione deve gestire il metodo
    public ResponseEntity<String> handleInvalidLetter(InvalidLetterException e) {
        // Il ResponseEntity permette di impostare stato HTTP e corpo della risposta
        return ResponseEntity.badRequest()              // Il codice di stato Http 400 Bad Request
                .contentType(MediaType.TEXT_PLAIN)      // Il corpo del messaggio contiene testo semplice
                .body(e.getMessage());                  // Inserisce il messaggio dell'errore nel corpo e restituisce la risposta completa
    }

    @ExceptionHandler(NoNamesFoundException.class)
    public ResponseEntity<String> handleNoNamesFound(NoNamesFoundException e) {
        return ResponseEntity.noContent()       // Imposta lo stato Http 204 No Content
                .build();                       // Conclude la costruzione del messaggio senza un corpo
    }
}

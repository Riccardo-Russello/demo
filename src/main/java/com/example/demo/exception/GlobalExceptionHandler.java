package com.example.demo.exception;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

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

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<String> handleValidation(HandlerMethodValidationException e) {
        String message = e.getParameterValidationResults().stream()     // restituisce i risultati della validazione raggruppati per parametro e li elabora in sequenza
                .flatMap(result -> result.getResolvableErrors().stream())   // riunisce gli elementi in un unico flusso
                .map(error -> error.getDefaultMessage())                  // estrae il messaggio di ogni errore
                .findFirst()                                                        // prende il primo messaggio disponibile e lo ritorna un Optional<String>
                .orElse("Parametro non valido");                                // testo di riserva se non viene trovato nessun messaggio

        return ResponseEntity.status(e.getStatusCode())     // usa lo stato dell'eccezione (400 Bad Request)
                .contentType(MediaType.TEXT_PLAIN)          // il corpo contiene testo semplice
                .body(message);                             // inserisce il messaggio nel corpo della risposta
    }
}

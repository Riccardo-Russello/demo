package com.example.demo.api;

import jakarta.validation.constraints.NotBlank;

// Definisce il contenuto delle richieste di creazione e modifica di una professione
public record ProfessionRequest(                // Record per trasportare i dati della richiesta
        @NotBlank(message = "Il nome della professione è obbligatorio")
        String jobName
) {
}

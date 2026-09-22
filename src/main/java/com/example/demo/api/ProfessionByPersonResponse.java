package com.example.demo.api;

// Definisce la risposta quanndo cerchi la professione di una persona
public record ProfessionByPersonResponse(       // Contiene i dati da restituire al cliente
        String profession
) { }

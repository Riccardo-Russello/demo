package com.example.demo.api;

import com.example.demo.service.ProfessionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v1/profession")
@RestController
public class ProfessionController {
    private final ProfessionService professionService;

    public ProfessionController(ProfessionService professionService) {
        this.professionService = professionService;
    }

    @GetMapping(path = "/by-person")
    public ProfessionByPersonResponse getProfessionByPerson(
            @RequestParam String name,
            @RequestParam String surname) {
        return professionService.getProfessionByPerson(name, surname);
    }
}

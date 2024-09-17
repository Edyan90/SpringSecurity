package Epicode.gestioneDipendenti.controllers;

import Epicode.gestioneDipendenti.entities.Dipendente;
import Epicode.gestioneDipendenti.exceptions.BadRequestEx;
import Epicode.gestioneDipendenti.recordsDTO.DipendenteDTO;
import Epicode.gestioneDipendenti.recordsDTO.LoginDTO;
import Epicode.gestioneDipendenti.services.AuthService;
import Epicode.gestioneDipendenti.services.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;
    @Autowired
    private DipendenteService dipendenteService;

    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO) {
        return this.authService.checkCredenzialiAndGeneraToken(loginDTO);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Dipendente create(@RequestBody @Validated DipendenteDTO dipendenteDTO, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            throw new BadRequestEx("Ci sono stati errori nel payload: " + messages);
        } else {
            return dipendenteService.save(dipendenteDTO);
        }
    }
}

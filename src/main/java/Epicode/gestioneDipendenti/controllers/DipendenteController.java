package Epicode.gestioneDipendenti.controllers;

import Epicode.gestioneDipendenti.entities.Dipendente;
import Epicode.gestioneDipendenti.exceptions.BadRequestEx;
import Epicode.gestioneDipendenti.recordsDTO.DipendenteDTO;
import Epicode.gestioneDipendenti.services.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dipendenti")
public class DipendenteController {
    @Autowired
    DipendenteService dipendenteService;

    @GetMapping
//    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Dipendente> findAll(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "nome") String sortBy) {
        return this.dipendenteService.findAll(page, size, sortBy);
    }

    @GetMapping("{dipendenteID}")
    public Dipendente findByID(@PathVariable UUID dipendenteID) {
        return this.dipendenteService.findByID(dipendenteID);
    }


    @PutMapping("/{dipendenteID}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Dipendente findAndUpdate(@PathVariable UUID dipendenteID, @RequestBody @Validated DipendenteDTO dipendenteDTO, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            throw new BadRequestEx("Ci sono stati errori nel payload. " + messages);
        } else {
            return this.dipendenteService.update(dipendenteID, dipendenteDTO);
        }
    }

    @DeleteMapping("/{dipendenteID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void findAndDelete(@PathVariable UUID dipendenteID) {
        this.dipendenteService.findAndDelete(dipendenteID);
    }

    @PostMapping("/{dipendenteID}/avatar")
    public Dipendente uploadAvatar(@PathVariable UUID dipendenteID, @RequestParam("avatar") MultipartFile image) throws IOException {
        return this.dipendenteService.avatarUpload(dipendenteID, image);
    }

    @GetMapping("/me")
    public Dipendente getProfile(@AuthenticationPrincipal Dipendente currentAuthenticatedDipendente) {
        return currentAuthenticatedDipendente;
    }

    @PutMapping("/me")
    public Dipendente updateProfile(@AuthenticationPrincipal Dipendente currentAuthenticatedDipendente, @RequestBody @Validated DipendenteDTO dipendenteDTO) {
        return this.dipendenteService.update(currentAuthenticatedDipendente.getId(), dipendenteDTO);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@AuthenticationPrincipal Dipendente currentAuthenticatedDipendente) {
        this.dipendenteService.findAndDelete(currentAuthenticatedDipendente.getId());
    }

}

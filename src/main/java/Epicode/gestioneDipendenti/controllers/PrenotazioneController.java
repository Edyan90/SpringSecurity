package Epicode.gestioneDipendenti.controllers;

import Epicode.gestioneDipendenti.entities.Prenotazione;
import Epicode.gestioneDipendenti.exceptions.BadRequestEx;
import Epicode.gestioneDipendenti.recordsDTO.PrenotazioneDTO;
import Epicode.gestioneDipendenti.services.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioneController {
    @Autowired
    PrenotazioneService prenotazioneService;

    @GetMapping
    public Page<Prenotazione> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dataDiRichiesta") String sortBy) {
        return this.prenotazioneService.findAll(page, size, sortBy);
    }

    @GetMapping("{prenotazioneID}")
    public Prenotazione findByID(@PathVariable UUID prenotazioneID) {
        return this.prenotazioneService.findByID(prenotazioneID);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Prenotazione created(@RequestBody @Validated PrenotazioneDTO prenotazioneDTO, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            throw new BadRequestEx("Ci sono stati errori nel payload: " + messages);
        } else {
            return this.prenotazioneService.save(prenotazioneDTO);
        }
    }

    @PutMapping("{prenotazioneID}")
    public Prenotazione update(@PathVariable UUID prenotazioneID, @RequestBody PrenotazioneDTO prenotazioneDTO, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            throw new BadRequestEx("Ci sono stati errori nel payload: " + messages);
        } else {
            return this.prenotazioneService.update(prenotazioneID, prenotazioneDTO);
        }
    }

    @DeleteMapping("{prenotazioneID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findAndDelete(@PathVariable UUID prenotazioneID) {
        this.prenotazioneService.findAndDelete(prenotazioneID);
    }


}

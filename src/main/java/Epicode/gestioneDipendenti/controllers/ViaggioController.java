package Epicode.gestioneDipendenti.controllers;

import Epicode.gestioneDipendenti.entities.Viaggio;
import Epicode.gestioneDipendenti.exceptions.BadRequestEx;
import Epicode.gestioneDipendenti.recordsDTO.ViaggioDTO;
import Epicode.gestioneDipendenti.recordsDTO.ViaggioStatusDTO;
import Epicode.gestioneDipendenti.services.ViaggioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/viaggi")
public class ViaggioController {
    @Autowired
    ViaggioService viaggioService;

    @GetMapping
    public Page<Viaggio> findAll(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "destinazione") String sortBy) {
        return this.viaggioService.findAll(page, size, sortBy);
    }

    @GetMapping("{viaggioID}")
    public Viaggio findByID(@PathVariable UUID viaggioID) {
        return this.viaggioService.findByID(viaggioID);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Viaggio create(@RequestBody @Validated ViaggioDTO viaggioDTO, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            throw new BadRequestEx("Ci sono stati errori nel payload: " + messages);
        } else {
            return this.viaggioService.save(viaggioDTO);
        }
    }

    @PutMapping("{viaggioID}")
    public Viaggio findAndUpdate(@PathVariable UUID viaggioID, @RequestBody @Validated ViaggioDTO viaggioDTO, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            throw new BadRequestEx(messages);
        } else {
            return this.viaggioService.upDate(viaggioID, viaggioDTO);
        }
    }

    @DeleteMapping("{viaggioID}")
    public void findAndDelete(@PathVariable UUID viaggioID) {
        this.viaggioService.findByIDAndDelete(viaggioID);
    }

    @PatchMapping("{viaggioID}/stato")
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public Viaggio updateStatus(@PathVariable UUID viaggioID, @RequestBody @Validated ViaggioStatusDTO viaggioStatusDTO, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            throw new BadRequestEx(messages);
        } else {
            return this.viaggioService.upDateStatus(viaggioID, viaggioStatusDTO);
        }
    }
}
